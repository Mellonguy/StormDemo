package com.storm.demo.example.webcrawler;

import java.util.Map;

import org.apache.storm.shade.org.apache.commons.lang.StringUtils;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebCrawlerBoltSearchWord extends BaseRichBolt{

	/**
	 *
	 */
	public WebCrawlerBoltSearchWord(Map<String, Object> _requestDataSet) {
		super();
		this.requestDataSet = _requestDataSet;
	}

	/**
	 *
	 */
	public WebCrawlerBoltSearchWord() {
		super();
	}

    private OutputCollector collector;
    private Map<String, Object> requestDataSet;


    @Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
    	System.out.println( "===============WebCrawlerBoltSearchWord declareOutputFields===============");
    	declarer.declare(new Fields("siteName","column","searchWord","count"));
    }

	/*
	 * @see org.apache.storm.task.IBolt#prepare(java.util.Map, org.apache.storm.task.TopologyContext, org.apache.storm.task.OutputCollector)
	 */
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		System.out.println( "===============WebCrawlerBoltSearchWord prepare===============");
		this.collector = collector;

	}


	/*
	 * @see org.apache.storm.task.IBolt#execute(org.apache.storm.tuple.Tuple)
	 */
	@Override
	public void execute(Tuple tuple) {
		System.out.println( "===============WebCrawlerBoltSearchWord execute===============");
		String siteName = tuple.getStringByField("siteName");
		String column = tuple.getStringByField("column");
		String contents = tuple.getStringByField("contents");
		String searchWord = String.valueOf(requestDataSet.get("searchWord"));
		boolean wordYN = contents.contains(searchWord);
		int count = 0;
		if(wordYN) {
			count = StringUtils.countMatches(contents, searchWord);
		}

		System.out.println( "siteName : column : searchWord : wordYN : count ================================="+ siteName +" : "+ column +" : "+ searchWord +" : "+ wordYN+" : "+count );

		this.collector.emit(new Values(siteName, column, searchWord, count));

	}

}
