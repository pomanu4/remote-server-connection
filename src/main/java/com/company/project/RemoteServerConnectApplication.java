package com.company.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.company.project.component.DataTransferObject;
import com.company.project.component.HttpRequestSender;
import com.company.project.component.RequestType;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@SpringBootApplication
public class RemoteServerConnectApplication implements CommandLineRunner {

    @Autowired
    HttpRequestSender sender;
    
    Logger logger = LogManager.getLogger(RemoteServerConnectApplication.class);

    public static void main(String ... args) {
        SpringApplication.run(RemoteServerConnectApplication.class, args);
    }

    @Override
    public void run(String ... arg0) throws Exception {
        
        Random random = new Random();
//        int[] services = new int[]{5592, 5595, 5597, 5598, 5599, 5600};
//        String[] accounts = new String[]{"4149499340447448","5269610000007865","4134170000012932"};
         int cancelId = 195;
         
//         String account =  "3632952"; // terminals
//        String account =  "42123242292";  ///// bitone
//        String account =  "41001614575714";
//        String account =  "48733387397";
//            String account =  "25700120202056919"; ///yman
//            String account =  "4012001037141112";
            String account =  "5182820100177963";  //// 5351 1803 0112 6706   5412 7103 0003 5305
        
        int id = 26561;
        
        int sum = random.nextInt(401)+100;
      
        DataTransferObject dto = new DataTransferObject(id, account, 1400, sum, 240);
        
        sender.sendHttpRequestWithSignature(dto, RequestType.VERIFY);
//	sender.sendHttpRequestWithSignature(dto, RequestType.PAYMENT);
//	sender.sendHttpRequestWithSignature(dto, RequestType.STATUS);
//      sender.sendHttpRequestWithSignature(dto, RequestType.CANCEL);
//        sender.sendHttpRequestWithSignature(dto, RequestType.ONLINE_ADVANCE);
//        sender.sendHttpRequestWithSignature(dto, RequestType.BALANCE);
//        sender.sendHttpRequest(dto, "equaring", id); //// 230 merch

        
        /*
        for (int i = 0; i < 5; i++) {
            id = id + 1;
            int loopSum = random.nextInt(401) + 100;
//            int index = random.nextInt(accounts.length);

            dto.setId(id);
//            dto.setSumm(loopSum);
//            dto.setAccount(accounts[index]);
//            dto.setService(5591);
//            System.out.println(dto.toString()); 
//
            sender.sendHttpRequestWithSignature(dto, RequestType.VERIFY);
            sender.sendHttpRequestWithSignature(dto, RequestType.PAYMENT);
//            sender.sendHttpRequestWithSignature(dto, RequestType.STATUS);

        }
        */
  //380674060606L // elpaySys userId
//        System.out.println(id);
//    sender.checkSignature("qwerty");
    logger.info("==> id " + id);
    
//        String ob = sender.sendGetRequest();
//        System.out.println(ob);
    }
}