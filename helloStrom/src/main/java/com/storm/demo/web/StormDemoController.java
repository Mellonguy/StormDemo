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
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
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

import com.storm.demo.example.webcrawler.WebCrawlerBolt;
import com.storm.demo.example.webcrawler.WebCrawlerSpout;
import com.storm.demo.example.webcrawler.WebCrawlerTopologyLocal;
import com.storm.demo.props.StormProps;
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

	private String spout = "crawlerSpout";
	private String bolt = "crawlerBolt";

    @Autowired
    private StormProps stormProps;
	@Autowired
	private WebCrawlerService webCrawlerService;


	@RequestMapping(value="/")
	public String home(){
		return "storm";
	}

	@ResponseBody
	@RequestMapping(value="startCrawler", produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> startCrawler(@RequestBody Map<String, Object> dataSet) throws ClientProtocolException, IOException, AlreadyAliveException, InvalidTopologyException, AuthorizationException{
		System.out.println("StormDemoController >>>>>>>>> START");
		Map<String, Object> response = new HashMap<String, Object>();
		// 웹사이트를 크롤해서 가져온다
		List<Object> _list = webCrawlerService.crawlerDataIgnoleHttpMap(dataSet);

		//Topology를 실행시킨다
		WebCrawlerTopologyLocal webCrawlerTopologyLocal = new WebCrawlerTopologyLocal();
      	webCrawlerTopologyLocal.webCrawlerTopologyLocal(stormProps, _list);




//		!!!!!!!!!!!!!!!!!!!!!테스트할때 쓰자@!!!!!!!!!!!!!!!!!
//		WordCountTopology wordCountTopology  = new WordCountTopology();
//		wordCountTopology.wordCountTopology();
//


		response.put("data", _list);

		return response;
	}

	@ResponseBody
	@RequestMapping(value="stopCrawler", produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> stopCrawler(@RequestBody Map<String, Object> dataSet) throws ClientProtocolException, IOException, AlreadyAliveException, InvalidTopologyException, AuthorizationException{
		System.out.println("StormDemoController >>>>>>>>> START");
		Map<String, Object> response = new HashMap<String, Object>();

		List<Object> _list = webCrawlerService.crawlerDataIgnoleHttpMap(dataSet);

		TopologyBuilder topologyBuilder = new TopologyBuilder();
		Config config = new Config();
		config.setDebug(true);
		config.setMaxTaskParallelism(3);
		config.setNumWorkers(3);

		topologyBuilder.setSpout(spout, new WebCrawlerSpout(_list),4);
		topologyBuilder.setBolt(bolt,new WebCrawlerBolt(),8).shuffleGrouping(spout);
//		topologyBuilder.setBolt("count",new WebCrawlerWordCountBolt(),12).fieldsGrouping(bolt, new Fields("word"));

        LocalCluster cluster = new LocalCluster();
        StormSubmitter.submitTopology(stormProps.getTopologyName(), config, topologyBuilder.createTopology());

//        StormSubmitter.submitTopologyWithProgressBar(stormProps.getTopologyName(), config, topologyBuilder.createTopology());

//		테스트할때 쓰자@!!!!!!!!!!!!!!!!!
//		HelloTopologyLocal helloTopologyLocal = new HelloTopologyLocal();
//		helloTopologyLocal.helloTopologyLocal();


//        cluster.killTopology(stormProps.getTopologyName());
//        cluster.shutdown();

		response.put("data", cluster);

		return response;
	}

}
