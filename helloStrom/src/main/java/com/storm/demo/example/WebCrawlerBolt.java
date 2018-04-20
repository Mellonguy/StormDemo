package com.storm.demo.example;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;
 
public class WebCrawlerBolt extends BaseBasicBolt{
 
        public void execute(Tuple tuple, BasicOutputCollector collector) {
               // TODO Auto-generated method stub
        	System.out.println("Bolt start  >>>>>>>>>>");
               String value = tuple.getStringByField("say");
               System.out.println("Tuple value is"+value);
               
        }
 
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
               // TODO Auto-generated method stub
              
        }
 
}
