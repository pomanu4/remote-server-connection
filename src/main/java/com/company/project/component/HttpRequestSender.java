package com.company.project.component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

@Component
public class HttpRequestSender {

    @Autowired
    private KeyBytesSupplier keySupplier;

    @Autowired
    private XmlSignatureUtill signatureUtill;

    @Autowired
    private DocumentFormatter formatter;

    private static RestTemplate DEFAULT_REST_TEMPLATE = new RestTemplate();
    private static final String BASE_URL = "https://test.lgaming.net/external/extended";
//    private static final String BASE_URL = "https://test.lgaming.net/external/extended-cert";//url for certificate
    private static final String HEADER_NAME = "PayLogic-Signature";
    private static final String CHARSET = "UTF-8";

    public ResponseEntity<String> sendHttpRequest(DataTransferObject dto, RequestType type){
        
//        RestTemplate restTemplate = null;
        RestTemplate restTemplate = HttpRequestSender.DEFAULT_REST_TEMPLATE;
//        try {
//           restTemplate = getRestTempWithCertificate();
//        } catch (KeyStoreException | IOException | KeyManagementException | NoSuchAlgorithmException | UnrecoverableKeyException | CertificateException ex) {
//            Logger.getLogger(HttpRequestSender.class.getName()).log(Level.SEVERE, null, ex);
//        }

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

    private RestTemplate sslConectionWithIgnoreSertificate() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        RestTemplate restTemplate = new RestTemplate(requestFactory);

        return restTemplate;
    }

    private RestTemplate getRestTempWithCertificate() throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, KeyManagementException, IOException, CertificateException {

        SSLContext sslContext = SSLContextBuilder.create().loadKeyMaterial(keySupplier.getKeyStore(), keySupplier.getPassword())
                .loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();

        HttpClient httpClient = HttpClients.custom().setSSLContext(sslContext).build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        RestTemplate restTemplate = new RestTemplate(requestFactory);

        return restTemplate;
    }
}
