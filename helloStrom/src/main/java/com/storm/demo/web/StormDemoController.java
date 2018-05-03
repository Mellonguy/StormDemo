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
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.storm.demo.example.WebCrawlerBolt;
import com.storm.demo.example.WebCrawlerSpout;
import com.storm.demo.example.WebCrawlerWordCountBolt;
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
	@RequestMapping(value="storm", produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> map(@RequestBody Map<String, Object> dataSet) throws ClientProtocolException, IOException, AlreadyAliveException, InvalidTopologyException, AuthorizationException{
		System.out.println("StormDemoController >>>>>>>>> START");
		Map<String, Object> response = new HashMap<String, Object>();

		List<Object> _list = webCrawlerService.crawlerDataIgnoleHttpMap(dataSet);

		TopologyBuilder topologyBuilder = new TopologyBuilder();
		Config config = new Config();
		config.setDebug(true);
		config.setMaxTaskParallelism(3);
//		config.setNumWorkers(3);

		topologyBuilder.setSpout(spout, new WebCrawlerSpout(_list),4);
		topologyBuilder.setBolt(bolt,new WebCrawlerBolt(),8).shuffleGrouping(spout);
		topologyBuilder.setBolt("count",new WebCrawlerWordCountBolt(),12).fieldsGrouping(bolt, new Fields("word"));

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(stormProps.getTopologyName(), config, topologyBuilder.createTopology());

//        StormSubmitter.submitTopologyWithProgressBar(stormProps.getTopologyName(), config, topologyBuilder.createTopology());

//		테스트할때 쓰자@!!!!!!!!!!!!!!!!!
//		HelloTopologyLocal helloTopologyLocal = new HelloTopologyLocal();
//		helloTopologyLocal.helloTopologyLocal();

        Utils.sleep(10000);
        cluster.killTopology(stormProps.getTopologyName());
        cluster.shutdown();

		response.put("data", cluster.getTopologyInfo(bolt));

		return response;
	}

}
