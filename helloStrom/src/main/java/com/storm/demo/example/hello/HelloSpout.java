/*
 * Copyright ⓒ [2018] KTH corp.All rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */
package com.storm.demo.example.hello;

/**
 * HelloSpout.java
 *
 * @author Kwon Ki Tae
 * @version 1.0.0
 * @see
 * @since 1.0
 */
import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

public class HelloSpout extends BaseRichSpout {
          private static final long serialVersionUID = 1L;
          private SpoutOutputCollector collector;

          @Override
		public void open(Map conf,TopologyContext context,SpoutOutputCollector collector){
               this.collector = collector;
          }

          @Override
		public void nextTuple(){
                 this.collector.emit(new Values("hello world"));
          }

          @Override
		public void declareOutputFields(OutputFieldsDeclarer declarer){
                 declarer.declare(new Fields("say"));
          }

}
