package com.storm.demo.example.webcrawler;
import java.io.IOException;
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
	public WebCrawlerSpout(Map<String, Object> _map) {
		super();
		this.mapData = _map;
	}

	private static final long serialVersionUID = 1L;
	private SpoutOutputCollector collector;
	private Map<String, Object> mapData;

      /**
     * @throws IOException
	 *
	 */



	@Override
	public void open(Map conf,TopologyContext context,SpoutOutputCollector _collector){
	       this.collector = _collector;
	  }

	@Override
	public void nextTuple(){

		System.out.println("\n WebCrawlerSpout.nextTuple() >>>>>>>>>  >>>>>>>.. START \n");
//		int i=0;
//		for (String ent : mapData.keySet()) {
//		System.out.println(">>>>>>> i >>>>>>>"+ent.toString() +" \n @@@@@@@@@@@@@@@@ \n"+ String.valueOf(mapData.get(ent)) );
//			this.collector.emit(new Values(ent.toString(), mapData.get(ent)));
			this.collector.emit(new Values(mapData));
//		}

	  }

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer){
	     declarer.declare(new Fields("crawlerSpout","column","alarm"));
	  }

    @Override
    public void ack(Object msgId) {
    	System.out.println("OK:"+msgId);
    }



}