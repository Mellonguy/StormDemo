package com.storm.demo.example;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

public class WebCrawlerBolt extends BaseRichBolt{
	protected OutputCollector collector;



        @Override
		public void declareOutputFields(OutputFieldsDeclarer declarer) {
               // TODO Auto-generated method stub
        	declarer.declare(new Fields("word"));

        }

		/*
		 * @see org.apache.storm.task.IBolt#prepare(java.util.Map, org.apache.storm.task.TopologyContext, org.apache.storm.task.OutputCollector)
		 */
		@Override
		public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
			// TODO Auto-generated method stub
			this.collector = collector;

		}


		/*
		 * @see org.apache.storm.task.IBolt#execute(org.apache.storm.tuple.Tuple)
		 */
		@Override
		public void execute(Tuple tuple) {
			// TODO Auto-generated method stub
			System.out.println("Bolt start  >>>>>>>>>>");
			String value = tuple.getStringByField("url");
			System.out.println("Tuple value is"+value);
		}

}
