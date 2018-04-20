package com.storm.demo.dao;

import java.io.Serializable;

public class EventDao implements Serializable{

	private String evnetString;
	private int count;
	private String url;

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
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

	@Override
	public String toString() {
		return "DataSet [evnetString=" + evnetString + ", count=" + count + "]";
	}

}
