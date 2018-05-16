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
import java.util.Map;

import org.apache.storm.shade.org.json.simple.JSONArray;
import org.apache.storm.shade.org.json.simple.JSONObject;



/**
 * WebCrawlerService.java
 *
 * @author Kwon Ki Tae
 * @version 1.0.0
 * @see
 * @since 1.0
 */
public interface WebCrawlerService  {



	/**
	 * @param object
	 * @return
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	//RAW 컨츠를 가지고 오자
	JSONArray getRawContents(Map request);

	// 각 사이트별 실시간 검색 조회
	JSONObject getTrendsContents(JSONObject request);

	// 각 사이트별 뉴스 검색
	JSONObject getNewsContents(JSONObject request) throws  IOException;


}
