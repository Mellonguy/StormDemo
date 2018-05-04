/*
 * Copyright ⓒ [2018] KTH corp.All rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */
package com.storm.demo.example.wordcount;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

public class ReportBolt extends BaseRichBolt {
    private HashMap<String, Long> counter = null;

    @Override
	public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.counter = new HashMap<String, Long>();
    }

    @Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        // not need to create Output Stream;
    }

    @Override
	public void execute(Tuple tuple) {
        String word = tuple.getStringByField("word");
        Long count = tuple.getLongByField("count");
        this.counter.put(word, count);
    }

    @Override
    public void cleanup() {
        System.out.println("------- FINAL COUNT -------");
        for (String key: this.counter.keySet()) {
            System.out.println(key + ": " + this.counter.get(key));
        }
        System.out.println("---------------------------");
    }
}
