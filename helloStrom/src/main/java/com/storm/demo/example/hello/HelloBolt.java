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

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

public class HelloBolt extends BaseBasicBolt{

        @Override
		public void execute(Tuple tuple, BasicOutputCollector collector) {
               // TODO Auto-generated method stub
               String value = tuple.getStringByField("say");
               System.out.println("Tuple value is"+value);
        }

        @Override
		public void declareOutputFields(OutputFieldsDeclarer declarer) {
               // TODO Auto-generated method stub

        }

}


