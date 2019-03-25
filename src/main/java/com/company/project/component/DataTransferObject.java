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
//                this.point = 429;//cert test

                this.point = 433; //test point
//                this.service = 4468; // owbet
//                this.service = 5572; //fakeErrorService
//        this.service = 5586; //elPaySystem

//        this.service = 5610; //bankEast
//        this.service = 5591; //pinbankKyiv
//        this.service = 5592; //pinbankKrop
//        this.service = 5595; //pinbankOdessa
//        this.service = 5597; //pinbankRivne
//        this.service = 5598; //pinbankChernivci
//        this.service = 5599; //pinbankZytomur
        this.service = 5600; //pinbankHerson
//        this.service = 5621; 
        
//                this.service = 4481;//viber
//        this.service = 4500;     
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
