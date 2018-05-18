package com.storm.demo.example.webcrawler;

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
	}


	/*
	 * @see org.apache.storm.task.IBolt#execute(org.apache.storm.tuple.Tuple)
	 */
	@Override
	public void execute(Tuple tuple) {
		System.out.println( "===============WebCrawlerBolt_Email execute===============");

		String siteName = tuple.getStringByField("siteName");
		String column = tuple.getStringByField("column");

		this.sendEmail.put(siteName, column);

	}

    @Override
    public void cleanup() {
        System.out.println("------- FINAL COUNT -------");
        for (String key: this.sendEmail.keySet()) {
            System.out.println(key + ": " + this.sendEmail.get(key));
        }
        System.out.println("---------------------------");
    }

}
