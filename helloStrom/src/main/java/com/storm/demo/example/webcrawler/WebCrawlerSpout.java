package com.storm.demo.example.webcrawler;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebCrawlerSpout extends BaseRichSpout {


	/**
	 *
	 */
	public WebCrawlerSpout(List<Object> _list) {
		super();
		this.list = _list;
	}

	private static final long serialVersionUID = 1L;
	private SpoutOutputCollector collector;
	private List<Object> list;

      /**
     * @throws IOException
	 *
	 */



	@Override
	public void open(Map conf,TopologyContext context,SpoutOutputCollector collector){
	       this.collector = collector;
	  }

	@Override
	public void nextTuple(){

		System.out.println("WebCrawlerSpout.nextTuple() WebCrawlerSpout.nextTuple() WebCrawlerSpout.nextTuple() WebCrawlerSpout.nextTuple() WebCrawlerSpout.nextTuple() WebCrawlerSpout.nextTuple() >>>>>>>>>  >>>>>>>.. START");
		for (Object object : list) {
			this.collector.emit(new Values(object));
		}



	  }

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer){
	     declarer.declare(new Fields("crawlerSpout"));
	  }

    @Override
    public void ack(Object msgId) {
    	System.out.println("OK:"+msgId);
    }



}