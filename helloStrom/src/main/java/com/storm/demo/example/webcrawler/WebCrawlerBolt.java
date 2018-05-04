package com.storm.demo.example.webcrawler;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.springframework.context.annotation.Configuration;

@Configuration
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
			System.out.println("Bolt start  Bolt startBolt startBolt startBolt startBolt startBolt startBolt startBolt startBolt start>>>>>>>>>>");

			String value = String.valueOf(tuple.getValueByField("crawlerSpout"));
			String words[] = value.split(" ");
//			System.out.println("Tuple value is"+value);
			for (String w : words) {
				collector.emit(new Values(w));
			}
			collector.ack(tuple);

		}

}
