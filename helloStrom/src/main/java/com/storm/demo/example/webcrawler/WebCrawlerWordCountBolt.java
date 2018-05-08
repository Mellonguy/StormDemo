/*
 * Copyright ⓒ [2018] KTH corp.All rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */
package com.storm.demo.example.webcrawler;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

/**
 * WebCrawlerWordCountBolt.java
 *
 * @author Kwon Ki Tae
 * @version 1.0.0
 * @see
 * @since 1.0
 */
public class WebCrawlerWordCountBolt extends BaseRichBolt{
	private HashMap<String, Long> counter = null;
	private OutputCollector collector;


	/*
	 * @see org.apache.storm.topology.IComponent#declareOutputFields(org.apache.storm.topology.OutputFieldsDeclarer)
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		System.out.println("WebCrawlerWordCountBolt  declareOutputFields WebCrawlerWordCountBolt  declareOutputFields >>>>>>>>>>>>>");
		declarer.declare(new Fields("word", "count"));
	}


	/*
	 * @see org.apache.storm.task.IBolt#prepare(java.util.Map, org.apache.storm.task.TopologyContext, org.apache.storm.task.OutputCollector)
	 */
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		// TODO Auto-generated method stub

		this.collector = collector;
		this.counter = new HashMap<String, Long>();

	}


	/*
	 * @see org.apache.storm.task.IBolt#execute(org.apache.storm.tuple.Tuple)
	 */
	@Override
	public void execute(Tuple tuple) {

		System.out.println("WebCrawlerWordCountBolt execute WebCrawlerWordCountBolt execute WebCrawlerWordCountBolt execute >>>>>>>>>>");
		String word = tuple.getStringByField("word");
        Long count = this.counter.get(word);
        count = count == null ? 1L : count + 1;
        this.counter.put(word, count);
        this.collector.emit(new Values(word, count));
	}


	@Override
    public void cleanup() {
    }
}
