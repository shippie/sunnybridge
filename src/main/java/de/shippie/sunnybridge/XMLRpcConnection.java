package de.shippie.sunnybridge;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.PostConstruct;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class XMLRpcConnection
{
	private final Logger log = LoggerFactory.getLogger(XMLRpcConnection.class);

	/** RPC XML address */
	@Value("${sunnybridge.xmlrpc.host:'127.0.0.1'}")
	private String hostLocal;

	@Value("${sunnybridge.xmlrpc.port:80}")
	private Integer hostPort;

	/** endpoint */
	@Value("${sunnybridge.xmlrpc.endpoint.statechange:/config/xmlapi/statechange.cgi}")
	private String rpcStateChange;

	private URI rpcStateChangeURI = null;

	@Autowired
	RpcMappingProps mappingProps;
	
	@Value("${sunnybridge.xmlrpc.timeout:5}")
	private int timeout = 5;
	RequestConfig config = RequestConfig.custom()
	  .setConnectTimeout(timeout * 1000)
	  .setConnectionRequestTimeout(timeout * 1000)
	  .setSocketTimeout(timeout * 1000).build();
	
	
	public XMLRpcConnection()
	{

	}

	@PostConstruct
	public void init() throws URISyntaxException
	{
		URIBuilder b = new URIBuilder();
		b.setScheme("http");
		b.setHost(hostLocal);
		b.setPort(Integer.valueOf(hostPort));
		b.setPath(rpcStateChange);
		rpcStateChangeURI = b.build();
	}

	public void writeValue(String ideId, String value)
	{

		try
		{
			BasicCookieStore cookieStore = new BasicCookieStore();
			CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).setUserAgent(
				"Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36")
			.setDefaultRequestConfig(config).build();

			URIBuilder getRequestURI = new URIBuilder(rpcStateChangeURI);
			getRequestURI.addParameter("ise_id", ideId);
			getRequestURI.addParameter("new_value", value);
			HttpGet httpRequest = new HttpGet(getRequestURI.build());

			log.info("Send Data to RPC XML HOMEMATIC: {}", httpRequest.toString());
			httpclient.execute(httpRequest);
			httpclient.close();
		}
		catch (URISyntaxException | IOException e)
		{
			log.error("ERROR XML RPC POST", e);
		}

	}
}
