/*
 * Copyright ⓒ [2018] KTH corp.All rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */
package com.storm.demo.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.jsoup.nodes.Document;

import clojure.lang.Obj;

/**
 * WebCrawlerService.java
 *
 * @author Kwon Ki Tae
 * @version 1.0.0
 * @see
 * @since 1.0
 */
public interface WebCrawlerService  {

	String getCurrentData();

	String crawlerDataWithHttpString(Map<String,String> args) throws ClientProtocolException, IOException;

	/**
	 * @param object
	 * @return
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	//test로 쓰고 나중에 지우자.. 중복..
	String crawlerDataIgnoleHttpString(Map<String, Object> response) throws AlreadyAliveException, InvalidTopologyException, AuthorizationException, IllegalArgumentException, IOException;


	Document crawlerDataIgnoleHttpDocument(Map<String,String> args) throws ClientProtocolException, IOException;

	List<String> crawlerDataIgnoleHttpLsit(Map<String,String> args) throws ClientProtocolException, IOException;

	Map<String, Object> crawlerDataIgnoleHttpMap(Map<String, Object> dataSet) throws ClientProtocolException, IOException;

	String getToString();

}
