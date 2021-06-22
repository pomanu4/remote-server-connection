package com.company.project.component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DataTransferObject {

    private int id;

    private int point;

    private int service;

    private String account;

    private int checkNumber;

    private int summ;

    private String date;

    private int personId;

    public DataTransferObject(int id, String account, int checkNumber, int summ, int personId) {
        super();
        this.id = id;
//                this.point = 274;//cert test
//        this.point = 331; //gpc pint
//        this.point = 433; //split test point
//        this.point = 459; //split test point
        this.point = 458; //test point
//            this.point = 337; // lviv test point
//        this.point = 20002; //test point java 8

            this.service = 4468;  ///test uah olways success
//            this.service = 5709; /// test allways fail 4242424242424242 acount to pass online verify
            
            
//            this.service = 4409;  /// gpc test service  
//        this.service = 5610; //bankSouth
//        this.service = 5591; //pinbankKyiv
//        this.service = 5592; //pinbankKrop
//        this.service = 5595; //pinbankOdessa
//        this.service = 5597; //pinbankRivne
//        this.service = 5598; //pinbankChernivci
//        this.service = 5599; //pinbankZytomur
//        this.service = 5600; //pinbankHerson
//        this.service = 5635; ///test
//        this.service = 5631; //wellsendTransfer
//        this.service = 5636; //wellsendPayout
//        this.service = 5625; // BetconstructAcquiring
//        this.service = 5637;  //privateMoney
//        this.service = 5640;  //concordV2
//          this.service = 4458; //concordPrpv
//        this.service = 5643; //fopPorech
//            this.service = 5644; //b-pay
//        this.service = 5645; ////  turbo
//        this.service = 5646; ////  swiftGarant
//        this.service = 5651; ////  swiftGarant-ps
//        this.service = 5647; ////  ukrGazBank
//        this.service = 5692;  ///parimatch v3
//        this.service = 5612;  /// test paylogic2 - success
//        this.service = 5696;  /// test paylogic2 - fail min sum 10
//         this.service = 4459;  /// test kyivstar
//          this.service = 5593;
//        this.service = 5650;  /// test doublePay

//           this.service = 4437; global
//             this.service = 5589; //familnyi
//             this.service = 5609; //familnyi + bank
//             this.service = 5652; //ding_test
//             this.service = 5653; ////                yanMon + split
//              this.service = 4471; /// ibox  
//              this.service = 5630; //// tassibox
//              this.service = 5657; //xPayout
//              this.service = 5658;  ///electroPay
//             this.service = 5659;  ///terminals
//             this.service = 5660;  ///biton
//            this.service = 5661;  ///zkTerm
//          this.service = 5662;  ///fcSystem
//            this.service = 5663;  ///codashop
//            this.service = 5664;  ///fimi + B2
//            this.service = 4786;  ///lviv test service
//            this.service = 5690;  ///lviv energy
//                this.service = 5691;  ///monobank  ///hermes  paystree  b2API  triolan  psRegister vodafon easyPay
//                this.service = 5630; /// tasKomIbox
//                 this.service = 5697;  // 472   Test Voucher Provider  сум ін != сум пров


//  this.service = 334;
        
        this.account = account;
        this.checkNumber = checkNumber;
        this.summ = summ;
        this.date = initDateField();
        this.personId = personId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(int checkNumber) {
        this.checkNumber = checkNumber;
    }

    public int getSumm() {
        return summ;
    }

    public void setSumm(int summ) {
        this.summ = summ;
    }

    public int getPoint() {
        return point;
    }

    public int getService() {
        return service;
    }

    public String getDate() {
        return date;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public void setService(int service) {
        this.service = service;
    }
    
    private String initDateField() {
        ZonedDateTime time = ZonedDateTime.now();
        DateTimeFormatter fopmater = DateTimeFormatter.ofPattern("YYYY-MM-dd'T'HH:mm:ss Z");
        return time.format(fopmater);
    }

    @Override
    public String toString() {
        return "DataTransferObject [id=" + id + ", point=" + point + ", service=" + service + ", account=" + account
                + ", checkNumber=" + checkNumber + ", summ=" + summ + ", date=" + date + "]";
    }

}
