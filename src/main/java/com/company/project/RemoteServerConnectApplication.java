package com.company.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.company.project.component.DataTransferObject;
import com.company.project.component.HttpRequestSender;
import com.company.project.component.RequestType;
import java.util.Random;

@SpringBootApplication
public class RemoteServerConnectApplication implements CommandLineRunner {

    @Autowired
    HttpRequestSender sender;

    public static void main(String[] args) {
        SpringApplication.run(RemoteServerConnectApplication.class, args);
    }

    @Override
    public void run(String... arg0) throws Exception {
        Random random = new Random();
        int id = 1484;
        
        
        DataTransferObject dto = new DataTransferObject(id, 380731521533L, 45887, random.nextInt(401)+100, 240);

        sender.sendHttpRequestWithSignature(dto, RequestType.VERIFY);
	sender.sendHttpRequestWithSignature(dto, RequestType.PAYMENT);
//	sender.sendHttpRequest(dto, RequestType.STATUS);
//      sender.sendHttpRequest(dto, RequestType.CANCEL);

//        sender.sendHttpRequestWithCertificate(dto, RequestType.VERIFY);
//        sender.sendHttpRequestWithCertificate(dto, RequestType.PAYMENT);
//        sender.sendHttpRequestWithCertificate(dto, RequestType.STATUS);
//        sender.sendHttpRequestWithCertificate(dto, RequestType.CANCEL);
       
            
        
  //380674060606L // elpaySys userId
        
//    sender.checkSignature("qwerty");

    }

}
