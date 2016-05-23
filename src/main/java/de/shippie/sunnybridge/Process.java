package de.shippie.sunnybridge;

import java.io.IOException;
import java.lang.reflect.Method;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import de.shippie.sunnybridge.model.PortalDataResult;

@Component
public class Process implements CommandLineRunner
{
	private final Logger log = LoggerFactory.getLogger(Process.class);

	@Autowired
	HttpPortalConnection httpPortalConnection;

	@Autowired
	XMLRpcConnection xmlRpcConnection;

	@Autowired
	RpcMappingProps rpcMappingsProps;

	@Autowired
	ShutdownManager shutdownManager;

	@SuppressWarnings("unchecked")
	@Scheduled(fixedDelayString = "${sunnybridge.request.inteval}", initialDelay = 1000)
	public void requestData()
	{
		try
		{
			CloseableHttpClient httpClient = httpPortalConnection.connect();

			if (httpClient != null)
			{
				httpPortalConnection.login(httpClient);
				PortalDataResult httpData = httpPortalConnection.getLiveData(httpClient);

				log.info("Received: {}", httpData);

				Method[] methodsJsonDataReq = ReflectionUtils.getAllDeclaredMethods(httpData.getClass());
				Map<String, Object> xmlProps = loadAllXmlRpcPropsValues();
				log.info("Loaded Props: {} ", xmlProps);

				if (httpData.getErrorMessages().size() > 0)
				{
					log.error("Data ERROR {}", StringUtils.join(httpData.getErrorMessages()));
					log.error("Send NO Request to XML RPC");
				}
				else
				{

					for (Method methodData : methodsJsonDataReq)
					{
						String methodName = methodData.getName();
						if (!StringUtils.startsWithIgnoreCase(methodName, "get"))
						{
							continue;
						}
						Object dataValue = ReflectionUtils.invokeMethod(methodData, httpData);
						String clazzName = (dataValue != null) ? dataValue.getClass().getName() : "-";
						log.info("Method Name: {} with value {} and Class-Type {}", methodName, dataValue, clazzName);

						XmlrpcPropertyPair keyProps = findPropertyValue(xmlProps, methodName);
						log.debug("XmlrpcPropertyPair {}", keyProps);
						if (keyProps.getIdValue() != null && keyProps.isOn())
						{
							log.info("Prepare Data for Request id {}, {}", keyProps.getIdValue(), dataValue);
							String cDataValue = convertDataValue(dataValue);
							if (cDataValue == null)
							{
								log.warn("Data Value is null...skip");
							}
							try
							{
								xmlRpcConnection.writeValue(keyProps.getIdValue(), cDataValue);
							}
							catch (ConnectTimeoutException e)
							{
								log.error("Socket timeout, break sending XML RPC Data...");
								break;
							}
						}
						else
						{
							log.info("Skip...is off");
						}
					}
				}
				httpClient.close();
			}
		}
		catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e)
		{
			log.error("Portal Error", e);
			throw new RuntimeException(e);
		}

	}

	@Override
	public void run(String... args) throws Exception
	{

		//shutdownManager.initiateShutdown(0);
	}

	private String convertDataValue(Object dataValue)
	{
		try
		{
			if (dataValue == null)
			{
				return "0";
			}
			else if (dataValue instanceof Integer)
			{
				return ((Integer) dataValue).toString();
			}
			else if (dataValue instanceof List)
			{
				return StringUtils.join(dataValue);
			}
			else
			{
				log.warn("Warning - not converted: {}", dataValue.getClass().getName());
				return (String) dataValue;
			}
		}
		catch (Exception e)
		{
			log.error("convert Error {}", e, e);
			return null;
		}
	}

	private XmlrpcPropertyPair findPropertyValue(Map<String, Object> xmlRpcProps, String searchStr)
	{
		XmlrpcPropertyPair xmlRpcPainr = new XmlrpcPropertyPair();
		searchStr = StringUtils.remove(searchStr, "get");
		searchStr = StringUtils.remove(searchStr, "is");
		searchStr = StringUtils.lowerCase(searchStr);
		String searchStr2 = searchStr + "_on";
		for (Entry<String, Object> elementKeyValue : xmlRpcProps.entrySet())
		{
			String elementKeyStr = StringUtils.remove(elementKeyValue.getKey(), "get");
			elementKeyStr = StringUtils.remove(elementKeyStr, "is");
			log.debug("Ist {} = {} ?", searchStr, elementKeyStr);
			if (StringUtils.equalsIgnoreCase(searchStr, elementKeyStr))
			{
				log.debug("ok - found Value");
				xmlRpcPainr.setIdValue(String.valueOf(elementKeyValue.getValue()));
			}
			else if (StringUtils.equalsIgnoreCase(searchStr2, elementKeyStr))
			{
				log.debug("ok - found OnOff");
				xmlRpcPainr.setOn(Boolean.valueOf(String.valueOf(elementKeyValue.getValue())));
			}
		}

		return xmlRpcPainr;
	}

	private Map<String, Object> loadAllXmlRpcPropsValues()
	{
		Map<String, Object> propsMap = new HashMap<>();
		Method[] methodsConfig = ReflectionUtils.getAllDeclaredMethods(rpcMappingsProps.getClass());
		for (Method method : methodsConfig)
		{
			String methodName = method.getName();
			if (StringUtils.startsWithIgnoreCase(methodName, "get")
				| StringUtils.startsWithIgnoreCase(methodName, "is"))
			{
				Object dataValue = ReflectionUtils.invokeMethod(method, rpcMappingsProps);
				String key = StringUtils.removeStart(methodName, "get");
				key = StringUtils.removeStart(key, "is");
				key = StringUtils.lowerCase(key);
				propsMap.put(key, dataValue);
			}
			else
			{
				continue;
			}

		}

		return propsMap;
	}

	private class XmlrpcPropertyPair
	{
		private String idValue;
		private boolean on;

		public final String getIdValue()
		{
			return idValue;
		}

		public final void setIdValue(String idValue)
		{
			this.idValue = idValue;
		}

		public final boolean isOn()
		{
			return on;
		}

		public final void setOn(boolean on)
		{
			this.on = on;
		}

		@Override
		public String toString()
		{
			return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
		}
	}

}
