package com.company.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.company.project.component.DataTransferObject;
import com.company.project.component.HttpRequestSender;
import com.company.project.component.RequestType;

@SpringBootApplication
public class RemoteServerConnectApplication implements CommandLineRunner {
		
	@Autowired
	HttpRequestSender sender;
	

	public static void main(String[] args) {
		SpringApplication.run(RemoteServerConnectApplication.class, args);		
	}
	
	@Override
	public void run(String... arg0) throws Exception {
		
		DataTransferObject dto = new DataTransferObject(21, 555, 45887, 100);
		
		sender.sendHttpRequest(dto, RequestType.VERIFY);
		sender.sendHttpRequest(dto, RequestType.PAYMENT);
		sender.sendHttpRequest(dto, RequestType.STATUS);
			
	}
	
}
