/*
 * Copyright ⓒ [2018] KTH corp.All rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */
package com.storm.demo.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.storm.demo.service.WebCrawlerService;

/**
 * StromDemoController.java
 *
 * @author Kwon Ki Tae
 * @version 1.0.0
 * @see
 * @since 1.0
 */
@Controller
public class StormDemoController {

	private static Logger logger = LoggerFactory.getLogger(StormDemoController.class);

	@Autowired
	private WebCrawlerService webCrawlerService;

	@RequestMapping(value="/")
	public String home(){
		return "storm";
	}

	@ResponseBody
	@RequestMapping(value="storm", produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> map(@RequestBody Map<String, Object> dataSet) throws ClientProtocolException, IOException{
		logger.info("Request Map.... - {}", dataSet);
		Map<String, Object> response = new HashMap<String, Object>();

		String data = webCrawlerService.crawlerDataIgnoleHttpString(dataSet);
		response.put("data", data);

		return response;
	}

}
