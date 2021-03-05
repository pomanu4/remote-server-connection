package com.company.project.component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import org.apache.commons.codec.binary.Hex;
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
import org.json.JSONObject;

@Component
public class HttpRequestSender {

    @Autowired
    private CertificateUtill certificateUtill;

    @Autowired
    private XmlSignatureUtill signatureUtill;

    @Autowired
    private DocumentFormatter formatter;

    private static RestTemplate DEFAULT_REST_TEMPLATE = new RestTemplate();
    
    
//        private static String BASE_URL = "https://10.0.100.104/external/extended"; /// GPC java 8
//    private static String BASE_URL = "https://test8.api4pay.com:8443/api"; /// test java 8  https://test8.api4pay.com:8443/api  https://test8.api4pay.com:8443/external/extended
//    private static String BASE_URL = "https://api.lgaming.net/external/extended";
//    private static String BASE_URL = "https://95.211.12.99:61443/server/";
    private static String BASE_URL = "https://test.api4pay.com/external/extended";
//    private static String BASE_URL = "https://192.168.88.66/external/extended";
//     private static String BASE_URL = "https://api.finteko.com.ua/external/extended";
    
    private static final String BASE_URL_CERT = "https://test.lgaming.net/external/extended-cert";//url for certificate
    private static final String HEADER_NAME = "PayLogic-Signature";
    private static final String CHARSET = "UTF-8";

    public ResponseEntity<String> sendHttpRequestWithSignature(DataTransferObject dto, RequestType type){

//        RestTemplate restTemplate = HttpRequestSender.DEFAULT_REST_TEMPLATE;
       
        byte[] documentBytes = XmlDocumentBuilder.buildXmlDocument(type, dto);
        String document;
        ResponseEntity<String> responce = null;
        try {
             RestTemplate restTemplate = certificateUtill.sslConectionWithIgnoreSertificate();
//            document = new String(documentBytes, CHARSET);
            document = new String(documentBytes, "utf-8");
            System.out.println(document);
            HttpEntity<String> request = new HttpEntity<>(document, createHttpHeader(dto, documentBytes));
            responce = restTemplate.exchange(BASE_URL, HttpMethod.POST, request, String.class);

            showResultInConsole(request, responce);

        } catch (UnsupportedEncodingException | SignatureException | KeyStoreException | KeyManagementException | NoSuchAlgorithmException e) {
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
    
    public ResponseEntity<String> sendHttpRequest(DataTransferObject dto, String type, int id){

        RestTemplate restTemplate = HttpRequestSender.DEFAULT_REST_TEMPLATE;
        String document = "";
//        String key = "a88220c3241b850901ae080504571d5d01bc60e6";///  230-a88220c3241b850901ae080504571d5d01bc60e6
        String key = "56406a817075201942b6e57e2520805bdbecd419";   /// 228-56406a817075201942b6e57e2520805bdbecd419 
        
if("meny".equals(type)){
            document = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><request point=\"458\"><menu/></request>";
        } else if("equaring".equals(type)){
//            String mess = "{\"account\":\"test@test.com\",\"amount\":10.55,\"currency\":\"UAH\",\"projectid\":95369,\"email\":\"test@test.com\",\"methodid\":\"testks\",\"requestid\":26482628,\"requestmethod\":\"create_order\",\"client_url\":\"https://google.com\",\"attributes_number\":\"0978887777\",\"merchantid\":228}";          
//String mess = "{\"account\":\"test@test.com\",\"amount\":\"10\",\"client_url\":\"https://google.com\",\"currency\":\"UAH\",\"email\":\"test@test.com\",\"merchantid\":230,\"methodid\":\"visamctest\",\"projectid\": 95384,\"requestid\":65535,\"requestmethod\":\"create_order\"}";
  String mess = "{\"account\":\"L-test\",\"amount\":\"9.00\",\"client_url\":\"http://localhost:8080/\",\"currency\":\"UAH\",\"email\":\"test@localhost\",\"merchantid\":228,\"methodid\":\"visamctest\",\"projectid\":95369,\"requestid\":1602162833,\"requestmethod\":\"create_order\"}";
//String mess = "{\"account\":\"test_account\",\"amount\":9.95,\"client_url\":\"http://localhost:8080/\",\"currency\":\"UAH\",\"email\":\"test@localhost\",\"merchantid\":228,\"methodid\":\"monotest\",\"projectid\":95369,\"requestid\":1602162833,\"requestmethod\":\"create_order\"}";


/// favorit protocol test          
//String mess = "{\"account\":\"test@test.com\",\"amount\":10.55,\"currency\":\"UAH\",\"projectid\":95551,\"email\":\"test@test.com\",\"methodid\":\"testks_new\",\"requestid\":700,\"requestmethod\":\"create_order\",\"client_url\":\"https://xxx.yyy.com\",\"attributes_number\":\"0687927871\",\"merchantid\":230}";
            
            
            
            JSONObject json = new JSONObject(mess);
            json.put("requestid", id);
            String[] toArray = json.keySet().toArray(new String[0]);
            Arrays.sort(toArray);
            StringBuilder builder = new StringBuilder();
            Arrays.stream(toArray).forEach((s) -> builder.append(json.get(s)));
            builder.append(key);
            System.out.println(builder.toString());
            String generateHash = generateHash(builder.toString(), "SHA1"); /// or SHA1
            json.put("sign", generateHash);
            
            String toString = json.toString();
            System.out.println(toString);
            document = toString;
            BASE_URL = "https://payment.leogaming.net/gateway/v2/";
        }
        
        ResponseEntity<String> responce = null;
        try {
           
            HttpEntity<String> request = new HttpEntity<>(document, createHttpHeader(dto, document.getBytes(CHARSET)));
            responce = restTemplate.exchange(BASE_URL, HttpMethod.POST, request, String.class);

//            showResultInConsole(request, responce);
            System.out.println(responce.getBody());
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return responce;
    }

    private HttpHeaders createHttpHeader(DataTransferObject dto, byte[] document) throws SignatureException, UnsupportedEncodingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_NAME, signatureUtill.sign(document));
        headers.add("Content-Type", "text/xml; charset=" + CHARSET);
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
            System.out.println(responce.getBody());
//            System.out.println(formatter.printDocument(responce.getBody()));
            
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
        String testRequest = "<?xml version=\"1.0\"?><request point=\"433\"><advanced function=\"TPPregistr\" service=\"444\"><attribute value=\"888\" name=\"TPPid\"/><attribute value=\"LeoTName in flower shop\" name=\"TPPname\"/><attribute value=\"Львівська\" name=\"TPPregion\"/><attribute value=\"с. Сокільники, Сокальський р-н.\" name=\"TPPcity\"/><attribute value=\"пров.\" name=\"TPPstreetType\"/><attribute value=\"Стрийська\" name=\"TPPstreetName\"/><attribute value=\"22\" name=\"TPPhouseNumber\"/><attribute value=\"ТОВ Леогеймінг\" name=\"TPPowner\"/><attribute value=\"ТОВ Процессинг Інк\" name=\"TPPprocessing\"/></advanced></request>";
        String signature = "X1NEzpqqwIYm5DigWoDp7kgeugLVEDwXPXnivNRzR4EtdT5J2+g9EdMF2QSfe4nDDEDEUoN3wnLHFzXutfQfvWSKn/PXs9vAWyQFxo0up9Xlk6i++P+eySjAQHOpcdfQgQuj+vDXLfsZZImPDB9YiJiUUpiCn5mhaEKFH8h0u2g=";
//            String sign = signatureUtill.sign(testRequest);
        boolean verify = signatureUtill.verify(testRequest, signature);

        System.out.println(verify);
    }
    
    public String sendGetRequest(){
//        String url = UriUtils.encode("https://xn--80aa7cln.com/api/?method=domaccbalance&key=sfhsjdlkhASLQ124rDKJFwds902fsdfASDJkd&currency=VNRUB", "UTF-8");
       

        String ob = DEFAULT_REST_TEMPLATE.getForObject("https://xn--80aa7cln.com/api/?method=domaccbalance&key=sfhsjdlkhASLQ124rDKJFwds902fsdfASDJkd&currency=VNRUB", String.class);
        return ob;
    }
    
     private static String generateHash(String message, String alg){
        try{
            MessageDigest md = MessageDigest.getInstance(alg);
            md.update(message.getBytes(CHARSET));
            return new String(Hex.encodeHex(md.digest()));
        }catch(Exception e){
            return null;
        }
    }
}
