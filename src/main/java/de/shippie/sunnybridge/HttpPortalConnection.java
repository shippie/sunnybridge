package de.shippie.sunnybridge;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.shippie.sunnybridge.model.PortalDataResult;

@Component
public class HttpPortalConnection
{

	/** Sunny Portal address */
	private static final String HOST = "https://www.sunnyportal.com";
	/** Login path, used for posting login data */
	private static final String LOGIN = HOST + "/Templates/Start.aspx";
	/** Path to LiveData JSON */
	private static final String LIVEDATA_JSON = HOST + "/homemanager";

	@Value("${sunnyportal.user}")
	private String sunnyportalUser;

	@Value("${sunnyportal.password}")
	private String sunnyportalPassword;

	@Value("${http.proxy.host:#{null}}")
	private String proxyHost;

	@Value("${http.proxy.port:80}")
	private Integer proxyport;

	private final Logger log = LoggerFactory.getLogger(HttpPortalConnection.class);

	//	@SuppressWarnings("deprecation")
	//	public CloseableHttpClient connect() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException
	//	{
	//
	//		SSLContextBuilder builder = new SSLContextBuilder();
	//
	//		builder.loadTrustMaterial(null, new TrustSelfSignedStrategy()
	//		{
	//			@Override
	//			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException
	//			{
	//				return true;
	//			}
	//		});
	//		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build(),
	//			SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	//
	//		BasicCookieStore cookieStore = new BasicCookieStore();
	//		CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore)
	//			.setSSLSocketFactory(sslsf).setRedirectStrategy(new LaxRedirectStrategy())
	//			.setUserAgent(
	//				"Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36")
	//			.build();
	//
	//		log.info("try to log in");
	//		log.info(" login done");
	//
	//		return httpclient;
	//	}

	public CloseableHttpClient connect() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException
	{
		SSLUtilities.trustAllHostnames();
		SSLUtilities.trustAllHttpsCertificates();
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager()
		{
			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers()
			{
				return null;
			}

			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
			{
				return;
			}

			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
			{
				return;
			}
		} };

		SSLContext sslcontext = SSLContext.getInstance("TLS");
		sslcontext.init(null, trustAllCerts, new java.security.SecureRandom());

		//do not set connection manager
		HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());

		HttpClientBuilder httpclientBuilder = HttpClients.custom().setUserAgent(
			"Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36");

		if (StringUtils.isNotBlank(proxyHost))
		{
			httpclientBuilder.setProxy(new HttpHost(proxyHost, proxyport));
		}
		CloseableHttpClient httpclient = httpclientBuilder.build();

		HttpGet httpGet = new HttpGet("https://www.sunnyportal.com/");
		try
		{
			CloseableHttpResponse reps = httpclient.execute(httpGet);
			log.debug("{}", reps.toString());
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}

		return httpclient;
	}

	public void login(CloseableHttpClient httpclient)
	{
		HttpPost httpost = new HttpPost(LOGIN);
		List<NameValuePair> nvps = new ArrayList<>();
		nvps.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$Logincontrol1$txtUserName", sunnyportalUser));
		nvps.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$Logincontrol1$txtPassword", sunnyportalPassword));
		nvps.add(new BasicNameValuePair("__EVENTTARGET", "ctl00$ContentPlaceHolder1$Logincontrol1$LoginBtn"));
		httpost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		try
		{

			CloseableHttpResponse response = httpclient.execute(httpost);
			HttpEntity entity = response.getEntity();
			log.info("Received Login Entitiy {}", entity.toString());
			EntityUtils.consume(entity);
			log.info("login done");
			response.close();

		}
		catch (IOException e)
		{
			log.error("Login failed throw {}", e);
		}

	}

	public PortalDataResult getLiveData(CloseableHttpClient httpclient)
	{
		HttpGet get = new HttpGet(LIVEDATA_JSON);
		CloseableHttpResponse response;

		try
		{
			response = httpclient.execute(get);
			HttpEntity entity = response.getEntity();

			String contentComplete = readContent(entity);

			PortalDataResult p = new PortalDataResult();
			ObjectMapper mapper = new ObjectMapper();
			PortalDataResult readedValue = mapper.readValue(contentComplete, PortalDataResult.class);
			response.close();
			return readedValue;
		}
		catch (IOException e)
		{
			log.error("getLiveData ClientProtocolException");
		}
		return null;

	}

	private String readContent(HttpEntity entity)
	{
		try
		{
			if (entity == null || entity.getContent() == null)
			{
				log.warn("HttpEntity / HttpEntity Content is null");
				return null;
			}

			return EntityUtils.toString(entity);
		}
		catch (ParseException | IOException e)
		{
			throw new RuntimeException(e);
		}

	}

}
