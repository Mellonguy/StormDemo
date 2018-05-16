package com.storm.demo.example.webcrawler;

import java.io.IOException;
import java.util.Map;

import org.apache.storm.shade.org.json.simple.JSONObject;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebCrawlerBolt_News extends BaseRichBolt{



    private OutputCollector collector;

    @Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
    	System.out.println( "===============WebCrawlerBolt_News declareOutputFields===============");
           // TODO Auto-generated method stub
    	declarer.declare(new Fields("siteName","column","contents"));
    }

	/*
	 * @see org.apache.storm.task.IBolt#prepare(java.util.Map, org.apache.storm.task.TopologyContext, org.apache.storm.task.OutputCollector)
	 */
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		// TODO Auto-generated method stub
		System.out.println( "===============WebCrawlerBolt_News prepare===============");
		this.collector = collector;
		// naver 실시간 검색 부분
		//

	}


	/*
	 * @see org.apache.storm.task.IBolt#execute(org.apache.storm.tuple.Tuple)
	 */
	@Override
	public void execute(Tuple tuple) {
		String siteName  = tuple.getStringByField("siteName");
		Document _doc = (Document) tuple.getValueByField("contents");

		JSONObject jo;
		try {
			jo = getNewsContents(siteName);
			System.out.println( "===============WebCrawlerBolt_News exceute==============="+jo.get("siteName") +" :"+jo.get("contents"));
			collector.emit(new Values(jo.get("siteName") ,"news", jo.get("contents")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(">>>>>>>>>>>>>> Bolt getStringByField >>>>>>>>>>>>>>>>  "+_doc.text());



		collector.ack(tuple);

	}

	/*
	 * @see com.storm.demo.service.WebCrawlerService#getNewsContents(java.util.Map)
	 * 사이트별 뉴스데이터 가져오기
	 */
	public JSONObject getNewsContents(String siteName) throws  IOException {

		Document data;
		JSONObject jsonobject = new JSONObject();


		// 뉴스 검색
		if("naver".equals(siteName)){
			data = Jsoup.connect("http://news.naver.com").get();;
		}else if("daum".equals(siteName)){
			data = Jsoup.connect("http://media.daum.net").get();;
		}else if("nate".equals(siteName)){
			data = Jsoup.connect("http://news.nate.com").get();;
		}else {
			data = Jsoup.connect("http://news.zum.com").get();;
		}

		System.out.println("getTrendsContents siteName : "+siteName);

		jsonobject.put("siteName", siteName);
		jsonobject.put("contents", data.text());

		System.out.println("getNewsContents jsonobject : "+jsonobject.get("siteName"));

		return jsonobject;

	}

}
