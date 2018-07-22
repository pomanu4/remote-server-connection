package com.company.project.component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DataTransferObject {
	
	private int id;
	
	private int point;
	
	private int service;
	
	private int account;
	
	private int checkNumber;
	
	private int summ;
	
	private String date;

	public DataTransferObject(int id, int account, int checkNumber, int summ) {
		super();
		this.id = id;
		this.point = 327;
		this.service = 4390;
		this.account = account;
		this.checkNumber = checkNumber;
		this.summ = summ;
		this.date = initDateField();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAccount() {
		return account;
	}

	public void setAccount(int account) {
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
	
	public String initDateField() {
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
