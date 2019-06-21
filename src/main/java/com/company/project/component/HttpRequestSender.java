package com.company.project.component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import org.apache.http.client.HttpClient;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

@Component
public class HttpRequestSender {

    @Autowired
    private CertificateUtill certificateUtill;

    @Autowired
    private XmlSignatureUtill signatureUtill;

    @Autowired
    private DocumentFormatter formatter;

    private static RestTemplate DEFAULT_REST_TEMPLATE = new RestTemplate();
    private static final String BASE_URL = "https://test.lgaming.net/external/extended";
    private static final String BASE_URL_CERT = "https://test.lgaming.net/external/extended-cert";//url for certificate
    private static final String HEADER_NAME = "PayLogic-Signature";
    private static final String CHARSET = "UTF-8";

    public ResponseEntity<String> sendHttpRequestWithSignature(DataTransferObject dto, RequestType type){

        RestTemplate restTemplate = HttpRequestSender.DEFAULT_REST_TEMPLATE;
        byte[] documentBytes = XmlDocumentBuilder.buildXmlDocument(type, dto);
        String document;
        ResponseEntity<String> responce = null;
        try {
            document = new String(documentBytes, CHARSET);
            HttpEntity<String> request = new HttpEntity<>(document, createHttpHeader(dto, documentBytes));
            responce = restTemplate.exchange(BASE_URL, HttpMethod.POST, request, String.class);

            showResultInConsole(request, responce);

        } catch (UnsupportedEncodingException | SignatureException e) {
            e.printStackTrace();
        }
        return responce;
    }
    
    public ResponseEntity<String> sendHttpRequestWithCertificate(DataTransferObject dto, RequestType type) {

        RestTemplate restTemplate = null;
        try {
            restTemplate = certificateUtill.getRestTempWithCertificate();
        } catch (KeyStoreException | IOException | KeyManagementException | NoSuchAlgorithmException | UnrecoverableKeyException | CertificateException ex) {
            Logger.getLogger(HttpRequestSender.class.getName()).log(Level.SEVERE, null, ex);
        }

        byte[] documentBytes = XmlDocumentBuilder.buildXmlDocument(type, dto);
        String document = null;
        try {
            document = new String(documentBytes, CHARSET);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(HttpRequestSender.class.getName()).log(Level.SEVERE, null, ex);
        }
        HttpEntity<String> request = new HttpEntity<>(document, new HttpHeaders());
        ResponseEntity<String> responce = restTemplate.exchange(BASE_URL_CERT, HttpMethod.POST, request, String.class);

        showResultInConsole(request, responce);

        return responce;

    }

    private HttpHeaders createHttpHeader(DataTransferObject dto, byte[] document) throws SignatureException {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_NAME, signatureUtill.sign(new String(document)));
        return headers;
    }

    private void showResultInConsole(HttpEntity<String> request, ResponseEntity<String> responce) {
        try {
            System.out.println("\n" + "Request document : \n");
            System.out.println(formatter.printDocument(request.getBody()));
            
            if(request.getHeaders().get(HEADER_NAME) != null){  
                System.out.println("Request signature : " + request.getHeaders().get(HEADER_NAME).get(0));
            }
            
            System.out.println("\n Responce document : \n");
            System.out.println(formatter.printDocument(responce.getBody()));
            
            if(responce.getHeaders().get(HEADER_NAME) != null){
                System.out.println("Responce signature : " + responce.getHeaders().get(HEADER_NAME).get(0));
                System.out.println("Signature verify : "
                    + signatureUtill.verify(responce.getBody(), responce.getHeaders().get(HEADER_NAME).get(0)) + "\n");
            }
        } catch (SignatureException e) {
            e.printStackTrace();
        }
    }

    public void checkSignature(String request) throws SignatureException {
        String testRequest = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><response><result id=\"14043187\" code=\"15\"/></response>";
        String signature = "OEiKo8I+2GOkmh+l5l8qDsXKf1+FctHp8j/0hAgwkafEOMRokRVcXlxcAvr+pQTSBDtalcoNpdZ7oArKoP+mDWX419CXegkWVfQZv8F0n11EQ2SIVAzLIJqFCbye/TcOTWpazuhrvMd3XjQFvLIsWAHu+jXBvNRqg5puEQs6Aqc=";
//            String sign = signatureUtill.sign(testRequest);
        boolean verify = signatureUtill.verify(testRequest, signature);

        System.out.println(verify);
    }
    
    public String sendGetRequest(){
//        String url = UriUtils.encode("https://xn--80aa7cln.com/api/?method=domaccbalance&key=sfhsjdlkhASLQ124rDKJFwds902fsdfASDJkd&currency=VNRUB", "UTF-8");
       

        String ob = DEFAULT_REST_TEMPLATE.getForObject("https://xn--80aa7cln.com/api/?method=domaccbalance&key=sfhsjdlkhASLQ124rDKJFwds902fsdfASDJkd&currency=VNRUB", String.class);
        return ob;
    }
}
