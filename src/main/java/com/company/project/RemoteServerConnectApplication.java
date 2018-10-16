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
        int id = 1481;
        
        
        DataTransferObject dto = new DataTransferObject(id, 380731521533L, 45887, random.nextInt(401)+100, 240);

        sender.sendHttpRequest(dto, RequestType.VERIFY);
//	sender.sendHttpRequest(dto, RequestType.PAYMENT);
//	sender.sendHttpRequest(dto, RequestType.STATUS);
        
//        sender.sendHttpRequest(dto, RequestType.CANCEL);
       
            
        
  //380674060606L // elpaySys userId
        
//    sender.checkSignature("qwerty");

    }

}
