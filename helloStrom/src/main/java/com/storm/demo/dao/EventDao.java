package com.storm.demo.dao;

import java.io.Serializable;

public class EventDao implements Serializable{
	
	private String evnetString;
	private int count;
	public String getEvnetString() {
		return evnetString;
	}
	public void setEvnetString(String evnetString) {
		this.evnetString = evnetString;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	

}
