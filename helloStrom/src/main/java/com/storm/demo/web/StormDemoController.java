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
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.storm.demo.example.webcrawler.WebCrawlerTopologyLocal;
import com.storm.demo.props.StormProps;

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


    @Autowired
    private StormProps stormProps;


	@RequestMapping(value="/")
	public String home(){
		return "storm";
	}

	@ResponseBody
	@RequestMapping(value="startCrawler", produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> startCrawler(@RequestBody Map<String, Object> requestDataSet) throws ClientProtocolException, IOException, AlreadyAliveException, InvalidTopologyException, AuthorizationException{
		System.out.println("StormDemoController >>>>>>>>> START");
		Map<String, Object> response = new HashMap<String, Object>();
		// 웹사이트를 크롤해서 가져온다
//		Map<String, Object> _map = webCrawlerService.crawlerDataIgnoleHttpMap(requestDataSet);
		TopologyBuilder tb = new TopologyBuilder();

		//Topology를 실행시킨다
		WebCrawlerTopologyLocal topology = new WebCrawlerTopologyLocal();
      	tb = topology.webCrawlerTopologyLocal(stormProps, requestDataSet);


//		!!!!!!!!!!!!!!!!!!!!!테스트할때 쓰자@!!!!!!!!!!!!!!!!!
//		WordCountTopology wordCountTopology  = new WordCountTopology();
//		wordCountTopology.wordCountTopology();
//

		response.put("data", response);

		return response;
	}

	@ResponseBody
	@RequestMapping(value="stopCrawler", produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> stopCrawler(@RequestBody Map<String, Object> dataSet) throws ClientProtocolException, IOException, AlreadyAliveException, InvalidTopologyException, AuthorizationException{
		System.out.println("StormDemoController >>>>>>>>> START");
		Map<String, Object> response = new HashMap<String, Object>();


		response.put("data", "");

		return response;
	}

}
