package com.storm.demo.example;

import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;

import com.storm.demo.service.WebCrawlerServiceImpl;

public class WebBrowserSpout extends BaseRichSpout{
	
	private SpoutOutputCollector collector;
	private WebCrawlerServiceImpl sc ;
	@Override
	public void nextTuple() {
		sc.getCurrentData();
	}

	@Override
	public void open(Map arg0, TopologyContext arg1, SpoutOutputCollector arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		// TODO Auto-generated method stub
		
	}
	

}
