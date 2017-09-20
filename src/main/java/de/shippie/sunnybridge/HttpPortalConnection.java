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
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

import de.shippie.sunnybridge.model.PortalDataResult;

@Component
public class HttpPortalConnection {

  /** Sunny Portal address */
  private static final String HOST = "https://www.sunnyportal.com";
  /** Login path, used for posting login data */
  // private static final String LOGIN = HOST + "/Templates/Start.aspx";

  /** Login path, used for posting login data */
  private static final String LOGIN = HOST + "/Templates/Login.aspx";


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

  private WebClient webClient;

  private final Logger log = LoggerFactory.getLogger(HttpPortalConnection.class);


  public CloseableHttpClient connect()
      throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
    SSLUtilities.trustAllHostnames();
    SSLUtilities.trustAllHttpsCertificates();
    TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
      @Override
      public java.security.cert.X509Certificate[] getAcceptedIssuers() {
        return null;
      }

      @Override
      public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
        return;
      }

      @Override
      public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
        return;
      }
    }};

    SSLContext sslcontext = SSLContext.getInstance("TLS");
    sslcontext.init(null, trustAllCerts, new java.security.SecureRandom());

    // do not set connection manager
    HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());

    HttpClientBuilder httpclientBuilder = HttpClients.custom().setUserAgent(
        "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36");

    if (StringUtils.isNotBlank(proxyHost)) {
      httpclientBuilder.setProxy(new HttpHost(proxyHost, proxyport));
    }
    httpclientBuilder.setRedirectStrategy(new LaxRedirectStrategy());
    CloseableHttpClient httpclient = httpclientBuilder.build();

    HttpGet httpGet =
        new HttpGet("https://www.sunnyportal.com/Templates/Start.aspx?ReturnUrl=%2fhomemanager");
    try {
      CloseableHttpResponse reps = httpclient.execute(httpGet);
      log.debug("{}", reps.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return httpclient;
  }

  public void login4() {
    try {
      if (webClient == null) {
        webClient = new WebClient();
        webClient.getOptions().setThrowExceptionOnScriptError(false);
      }
      final HtmlPage loginPage = webClient.getPage(LOGIN);

      final List<HtmlForm> form = loginPage.getForms();

      HtmlForm loginForm = null;
      for (HtmlForm htmlForm : form) {
        log.debug("FORM {} found", htmlForm.getId());
        if (StringUtils.equals(htmlForm.getId(), "aspnetForm")) {
          log.debug("aspnetForm found...ok");
          loginForm = htmlForm;
          break;
        }
      }
      if (loginForm == null) {
        throw new IllegalStateException("Loginform nicht erkannt... ");
      }
      HtmlSubmitInput buttonNode = findHtmlSubmit(loginForm.getChildNodes());
      HtmlInput inputUser =
          loginForm.getInputByName("ctl00$ContentPlaceHolder1$LoginControl1$txtUserName");
      inputUser.setValueAttribute(sunnyportalUser);
      HtmlInput inputPass =
          loginForm.getInputByName("ctl00$ContentPlaceHolder1$LoginControl1$txtPassword");
      inputPass.setValueAttribute(sunnyportalPassword);

      HtmlCheckBoxInput remainLoggedIn = findHtmlCheckBoxInput(loginForm.getChildNodes());
      remainLoggedIn.setChecked(true);

      Page pageClicked = buttonNode.click();
      log.info("CLick Done {}", pageClicked.getUrl());



    } catch (FailingHttpStatusCodeException | IOException e) {
      log.error("Fehler im Anmeldeprozess {}", e);
      throw new IllegalStateException(e);
    }
  }

  public PortalDataResult getLiveData() {
    Page page2 = null;
    try {
      if (webClient == null) {
        login4();
      }
      return getPageWithLiveData(webClient);
    } catch (Exception e) {
      // 2. Versuch...vorher neu anmelden
      login4();
      return getPageWithLiveData(webClient);
    }
  }

  private PortalDataResult getPageWithLiveData(WebClient webClient) {
    Page page2;
    try {
      page2 = webClient.getPage(LIVEDATA_JSON);
      String contentAsString = page2.getWebResponse().getContentAsString();
      log.info("Page 2 {}", contentAsString);

      PortalDataResult p = new PortalDataResult();
      ObjectMapper mapper = new ObjectMapper();
      return mapper.readValue(contentAsString, PortalDataResult.class);


    } catch (FailingHttpStatusCodeException | IOException e) {
      log.error("Fehler bei der Abfrage der Live Daten {}", e);
      throw new RuntimeException(e);
    }
  }


  public PortalDataResult getLiveData2(CloseableHttpClient httpclient) {
    HttpGet get = new HttpGet(LIVEDATA_JSON);
    CloseableHttpResponse response;

    try {
      response = httpclient.execute(get);
      HttpEntity entity = response.getEntity();

      String contentComplete = readContent(entity);
      log.info(contentComplete);

      PortalDataResult p = new PortalDataResult();
      ObjectMapper mapper = new ObjectMapper();
      PortalDataResult readedValue = mapper.readValue(contentComplete, PortalDataResult.class);
      response.close();
      return readedValue;
    } catch (IOException e) {
      log.error("getLiveData ClientProtocolException", e);
    }
    return null;

  }

  public HtmlSubmitInput findHtmlSubmit(DomNodeList<DomNode> domNodeList) {
    List<DomNode> allInputNodes = new ArrayList<>();
    inputList(allInputNodes, domNodeList);
    for (DomNode domNode : allInputNodes) {
      log.debug("List Element: {}", domNode);
      if (domNode instanceof HtmlSubmitInput) {
        HtmlSubmitInput ht = (HtmlSubmitInput) domNode;

        log.debug("HtmlSubmitInput Element: {}", ht);
        return ht;
      }
    }
    return null;
  }

  public HtmlCheckBoxInput findHtmlCheckBoxInput(DomNodeList<DomNode> domNodeList) {
    List<DomNode> allInputNodes = new ArrayList<>();
    inputList(allInputNodes, domNodeList);
    for (DomNode domNode : allInputNodes) {
      log.debug("List Element: {}", domNode);
      if (domNode instanceof HtmlCheckBoxInput) {
        HtmlCheckBoxInput ht = (HtmlCheckBoxInput) domNode;

        log.debug("HtmlCheckBoxInput Element: {}", ht);
        return ht;
      }
    }
    return null;
  }

  private void inputList(List<DomNode> nodeFoundList, DomNodeList<DomNode> domNodeList) {
    for (DomNode domNode : domNodeList) {
      if (StringUtils.equals(domNode.getNodeName(), "input")) {
        log.debug("Found element input, add... -> {}", domNode.getNodeName());
        nodeFoundList.add(domNode);

      } else {
        log.debug("Skip element {}", domNode.getNodeName());
      }
      if (domNode.hasChildNodes()) {
        inputList(nodeFoundList, domNode.getChildNodes());
      }
    }
  }


  public DomNode showChildren(DomNodeList<DomNode> domNodeList) {
    DomNode returnValue = null;
    for (DomNode domNode : domNodeList) {
      if (returnValue != null) {
        break;
      }
      log.info("Node Name: {}", domNode.getNodeName());
      // if (StringUtils.equals(domNode.getNodeName(), "input")) {
      if (domNode.hasAttributes()) {
        // return domNode;
        if (isLoginAttributes(domNode.getAttributes())) {
          return domNode;
        }
        if (domNode.hasChildNodes()) {
          returnValue = showChildren(domNode.getChildNodes());
        }

      }

      // }
    }
    return returnValue;
  }

  public boolean isLoginAttributes(org.w3c.dom.NamedNodeMap namedNodeMap) {
    for (int i = 0; i < namedNodeMap.getLength(); i++) {
      log.info("Att: {}", namedNodeMap.item(i));
      if (StringUtils.equals(namedNodeMap.item(i).getNodeValue(),
          "ctl00$ContentPlaceHolder1$LoginControl1$LoginBtn")) {
        log.info("**** FOUND ***** ");
        return true;
      }
    }
    return false;


  }



  private String readContent(HttpEntity entity) {
    try {
      if (entity == null || entity.getContent() == null) {
        log.warn("HttpEntity / HttpEntity Content is null");
        return null;
      }

      return EntityUtils.toString(entity);
    } catch (ParseException | IOException e) {
      log.error("Error Reading Content {}", e);
      throw new RuntimeException(e);
    }

  }

}
