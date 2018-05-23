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
public class WebCrawlerBolt_SMS extends BaseRichBolt{

	private Map<String, String> sendSMS = null;
	private OutputCollector collector;
	int x = 0;
    @Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
//    	System.out.println( x++ + " ===============WebCrawlerBolt_SMS declareOutputFields===============");
    }

	/*
	 * @see org.apache.storm.task.IBolt#prepare(java.util.Map, org.apache.storm.task.TopologyContext, org.apache.storm.task.OutputCollector)
	 */
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
//		System.out.println( x++ + "===============WebCrawlerBolt_SMS prepare===============");
		this.sendSMS = new HashMap<String, String>();
		this.collector = collector;
	}


	/*
	 * @see org.apache.storm.task.IBolt#execute(org.apache.storm.tuple.Tuple)
	 */
	@Override
	public void execute(Tuple tuple) {

		System.out.println(x++ + ">>>>>>tuple.getValues()>>>>>>>>>>>>>>>"+tuple.getFields() + ": "+tuple.getValues());
		String siteName = tuple.getStringByField("siteName");
		String column = tuple.getStringByField("column");
		String searchWord = tuple.getStringByField("searchWord");
		int count = tuple.getIntegerByField("count");

		sendSMS.put("siteName", siteName);
		sendSMS.put("column", column);
		sendSMS.put("searchWord", searchWord);
		sendSMS.put("count", String.valueOf(count));

//		System.out.println( x++ +" ===============WebCrawlerBolt_SMS execute==============="+siteName +" : "+column+" : "+searchWord+" : "+count);
		collector.ack(tuple);
	}

    @Override
    public void cleanup() {
        if(!sendSMS.isEmpty()) {
        	System.out.println("-------SMS 발송-------");
	        for (String key: this.sendSMS.keySet()) {
	            System.out.println(key + ": " + this.sendSMS.get(key));
	        }
        }
        System.out.println("-------------------------");
    }



}
