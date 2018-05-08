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

	/**
	 *
	 */
	public WebCrawlerBolt(Map<String, Object> _requestDataSet) {
		super();
		this.requestDataSet = _requestDataSet;
	}

	/**
	 *
	 */
	public WebCrawlerBolt() {
		super();
	}

    private OutputCollector collector;
    private Map<String, Object> requestDataSet;




    @Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
           // TODO Auto-generated method stub
    	declarer.declare(new Fields("keyword","requestDataSet"));

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
		System.out.println("Bolt start  Bolt startBolt startBolt startBolt startBolt startBolt startBolt startBolt startBolt start>>>>>>>>>>"+ requestDataSet.toString());

		String value = String.valueOf(tuple.getValueByField("crawlerSpout"));

		System.out.println(">>>>>>>>>>>>>> Bolt execute Tuple value is >>>>>>>>>>>>>>>>"+value.length());
		System.out.println(">>>>>>>>>>>>>> Bolt execute Tuple value is >>>>>>>>>>>>>>>>"+requestDataSet.keySet() + " : "+requestDataSet.entrySet());

		collector.emit(new Values(value,requestDataSet.entrySet() ));

//			String words[] = value.split(" ");

//			for (String w : words) {
//				collector.emit(new Values(w));
//			}
		collector.ack(tuple);

	}

}
