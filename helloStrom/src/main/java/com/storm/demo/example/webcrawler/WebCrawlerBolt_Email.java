package com.storm.demo.example.webcrawler;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebCrawlerBolt_Email extends BaseRichBolt{

	private Map<String, String> sendEmail = null;
	private OutputCollector collector;
	int x = 0;

    @Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
    	System.out.println( "===============WebCrawlerBolt_Email declareOutputFields===============");
    }

	/*
	 * @see org.apache.storm.task.IBolt#prepare(java.util.Map, org.apache.storm.task.TopologyContext, org.apache.storm.task.OutputCollector)
	 */
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		System.out.println( "===============WebCrawlerBolt_Email prepare===============");
//		this.sendEmail = new HashMap<String, String>();
		this.sendEmail = new HashMap<String, String>();
		this.collector = collector;
	}


	/*
	 * @see org.apache.storm.task.IBolt#execute(org.apache.storm.tuple.Tuple)
	 */
	@Override
	public void execute(Tuple tuple) {
		System.out.println( "===============WebCrawlerBolt_Email execute===============");


		String siteName = tuple.getStringByField("siteName");
		String column = tuple.getStringByField("column");
		String searchWord = tuple.getStringByField("searchWord");
		int count = tuple.getIntegerByField("count");

		sendEmail.put("siteName", siteName);
		sendEmail.put("column", column);
		sendEmail.put("searchWord", searchWord);
		sendEmail.put("count", String.valueOf(count));

//		System.out.println( x++ +" ===============WebCrawlerBolt_SMS execute==============="+siteName +" : "+column+" : "+searchWord+" : "+count);
		collector.ack(tuple);
	}

    @Override
    public void cleanup() {

    	 if(!sendEmail.isEmpty()) {
         	System.out.println("-------EMAIL 발송-------");

	        for (String key: this.sendEmail.keySet()) {
	            System.out.println(key + ": " + this.sendEmail.get(key));
	        }
        System.out.println("---------------------------");

    	 }
    }

}
