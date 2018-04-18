package com.storm.demo.example;
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
         
          public void open(Map conf,TopologyContext context,SpoutOutputCollector collector){
               this.collector = collector; 
          }
         
          public void nextTuple(){
        	  System.out.println("Spout start  >>>>>>>>>>");
                 this.collector.emit(new Values("hello world"));
          }
         
          public void declareOutputFields(OutputFieldsDeclarer declarer){
                 declarer.declare(new Fields("say"));
          }
         
}