package de.shippie.sunnybridge;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
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

	private final Logger log = LoggerFactory.getLogger(HttpPortalConnection.class);

	public CloseableHttpClient connect() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException
	{
		//		org.apache.http.ssl.SSLContextBuilder builder = new org.apache.http.ssl.SSLContextBuilder();
		//		builder.loadTrustMaterial(null, new TrustSelfSignedStrategy()
		//		{
		//
		//			@Override
		//			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException
		//			{
		//				return true;
		//			}
		//		});
		//
		//		SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
		// Allow TLSv1 protocol only, use NoopHostnameVerifier to trust self-singed cert
		//sslcontext,        new String[] { "TLSv1" }, null, new NoopHostnameVerifier()
		//(            final SSLContext sslContext, final HostnameVerifier hostnameVerifier) 
		//		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build(),
		//			SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

		//		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build(),
		//			SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

		SSLContextBuilder builder = new SSLContextBuilder();

		builder.loadTrustMaterial(null, new TrustSelfSignedStrategy()
		{
			@Override
			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException
			{
				return true;
			}
		});
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build(),
			SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

		BasicCookieStore cookieStore = new BasicCookieStore();
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore)
			.setSSLSocketFactory(sslsf)
			.setRedirectStrategy(new LaxRedirectStrategy())
			.setUserAgent(
				"Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36")
			.build();

		log.info("try to log in");

		log.info(" login done");

		//		// enter the endless loop
		//		while (!stop_Thread)
		//		{
		//
		//			try
		//			{
		//
		//				getLiveData(httpclient);
		//
		//			}
		//			catch (ParseException e)
		//			{
		//
		//				// if we have a parse error it could be that we got the login page instead of JSON
		//				SuPoxyUtils.log("JSON parse error - try re-login...");
		//				login(httpclient);
		//				SuPoxyUtils.log("JSON parse error - re-login done");
		//
		//			}
		//
		//			Thread.sleep(SuPoxySettings.requestinterval * 1000);
		//
		//		}

		return httpclient;
	}

	public CloseableHttpClient test() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException
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

		//SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
		SSLContext sslcontext = SSLContext.getInstance("TLS");
		sslcontext.init(null, trustAllCerts, new java.security.SecureRandom());
		//		// Allow TLSv1 protocol only, use NoopHostnameVerifier to trust self-singed cert
		//		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,
		//			new String[] { "TLSv1", "TLSv1.1" }, null, new NoopHostnameVerifier());

		//do not set connection manager
		HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());

		CloseableHttpClient httpclient = HttpClients.custom()
			.setUserAgent(
				"Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36")
			.build();

		HttpGet httpGet = new HttpGet("https://www.sunnyportal.com/");
		try
		{
			CloseableHttpResponse reps = httpclient.execute(httpGet);
			log.debug("{}", reps.toString());
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return httpclient;
	}

	public void login(CloseableHttpClient httpclient)
	{
		HttpPost httpost = new HttpPost(LOGIN);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$Logincontrol1$txtUserName", sunnyportalUser));
		nvps.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$Logincontrol1$txtPassword", sunnyportalPassword));
		nvps.add(new BasicNameValuePair("__EVENTTARGET", "ctl00$ContentPlaceHolder1$Logincontrol1$LoginBtn"));
		httpost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		try
		{

			CloseableHttpResponse response = httpclient.execute(httpost);
			HttpEntity entity = response.getEntity();
			EntityUtils.consume(entity);
			log.info("login done");
			response.close();

		}
		catch (IOException e)
		{
			log.error("Login failed throw", e);
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

			//			log.debug("Entity: {}",
			//				(entity != null && entity.getContent() != null) ? EntityUtils.toString(entity) : "<null>");

			String contentComplete = readContent(entity);

			PortalDataResult p = new PortalDataResult();
			ObjectMapper mapper = new ObjectMapper();
			PortalDataResult readedValue = mapper.readValue(contentComplete, PortalDataResult.class);
			response.close();
			return readedValue;

			//			SuPoxyDataObject data;
			//			data = new SuPoxyDataObject(SuPoxyUtils.fromStream(entity.getContent()));
			//
			//			// handle Portal errors
			//			if (data.getErrorMessages().length > 0)
			//			{
			//
			//				// Session expired - re-login
			//				if (data.getErrorMessages()[0].contains("Your session has expired. Please login again"))
			//				{
			//					login(httpclient);
			//				}
			//
			//			}
			//
			//			// if the cache is full we delete the first (oldest) entry before adding a new one
			//			while (SuPoxyServer.SunnyList.size() > SuPoxySettings.cachesize)
			//			{
			//				SuPoxyServer.SunnyList.remove(0);
			//			}
			//			SuPoxyServer.SunnyList.add(data);

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
