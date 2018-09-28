package com.company.project.component;

import java.io.UnsupportedEncodingException;
import java.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpRequestSender {

	@Autowired
	private XmlSignatureUtill signatureUtill;

	@Autowired
	private DocumentFormatter formatter;

	private static RestTemplate restTemplate = new RestTemplate();
	private static final String BASE_URL = "https://test.lgaming.net/external/extended";
	private static final String HEADER_NAME = "PayLogic-Signature";
	private static final String CHARSET = "UTF-8";
        
	public ResponseEntity<String> sendHttpRequest(DataTransferObject dto, RequestType type) {
		byte[] documentBytes = XmlDocumentBuilder.buildXmlDocument(type, dto);
		String document;
		ResponseEntity<String> responce = null;
		try {
			document = new String(documentBytes, CHARSET);
			HttpEntity<String> request = new HttpEntity<String>(document, createHttpHeader(dto, documentBytes));
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
			System.out.println("Request signature : " + request.getHeaders().get(HEADER_NAME).get(0));
			System.out.println("\n Responce document : \n");
			System.out.println(formatter.printDocument(responce.getBody()));
			System.out.println("Responce signature : " + responce.getHeaders().get(HEADER_NAME).get(0));

			System.out.println("Signature verify : "
					+ signatureUtill.verify(responce.getBody(), responce.getHeaders().get(HEADER_NAME).get(0)) + "\n");
		} catch (SignatureException e) {
			e.printStackTrace();
		}
	}
        
        public void checkSignature(String request) throws SignatureException{
            String testRequest = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><response><result id=\"14043187\" code=\"15\"/></response>";
            String signature = "OEiKo8I+2GOkmh+l5l8qDsXKf1+FctHp8j/0hAgwkafEOMRokRVcXlxcAvr+pQTSBDtalcoNpdZ7oArKoP+mDWX419CXegkWVfQZv8F0n11EQ2SIVAzLIJqFCbye/TcOTWpazuhrvMd3XjQFvLIsWAHu+jXBvNRqg5puEQs6Aqc=";
//            String sign = signatureUtill.sign(testRequest);
            boolean verify = signatureUtill.verify(testRequest, signature);
            
            System.out.println(verify);
        }

}
