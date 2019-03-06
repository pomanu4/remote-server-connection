package com.company.project;

import java.io.UnsupportedEncodingException;
import java.security.SignatureException;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.company.project.component.DataTransferObject;
import com.company.project.component.HttpRequestSender;
import com.company.project.component.RequestType;
import java.io.IOException;
import java.security.cert.CertificateException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {RemoteServerConnectApplication.class})
public class RemoteServerConnectApplicationTests {
	
	@Autowired
	private HttpRequestSender sender;
	
	static DataTransferObject dto;
	private static final String HEADER_NAME = "PayLogic-Signature";
	
	@Before
	public void initDto() {
		dto = new DataTransferObject(21, "555", 45887, 100, 240);
	}
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void testSendHttpRequest() throws SignatureException, UnsupportedEncodingException, IOException, CertificateException {
		
		ResponseEntity<String> responce = sender.sendHttpRequestWithSignature(dto, RequestType.VERIFY);
		
		Assertions.assertTrue(responce.getStatusCode() == HttpStatus.OK);
		Assertions.assertTrue(responce.getHeaders().containsKey(HEADER_NAME));
		Assertions.assertTrue(responce.getBody().getClass() == String.class);
	}
}
