package com.storm.demo.example.webcrawler;

import java.util.Map;

import org.apache.storm.shade.org.json.simple.JSONObject;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebCrawlerBolt_Trend extends BaseRichBolt{

    private OutputCollector collector;

	/*
	 * @see org.apache.storm.task.IBolt#prepare(java.util.Map, org.apache.storm.task.TopologyContext, org.apache.storm.task.OutputCollector)
	 */
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		// TODO Auto-generated method stub
		System.out.println( "===============WebCrawlerBolt_Trend prepare===============");
		this.collector = collector;
		// naver 실시간 검색 부분

	}


	/*
	 * @see org.apache.storm.task.IBolt#execute(org.apache.storm.tuple.Tuple)
	 */
	@Override
	public void execute(Tuple tuple) {

		String siteName  = tuple.getStringByField("siteName");
		Document _doc = (Document) tuple.getValueByField("contents");
		System.out.println( "===============WebCrawlerBolt_Trend exceute1==============="+siteName );

		JSONObject jo = getTrendsContents(siteName, _doc);

		System.out.println( "===============WebCrawlerBolt_Trend exceute2==============="+jo.get("siteName") +" :"+jo.get("contents"));
		collector.emit(new Values(jo.get("siteName") == null ? "": jo.get("siteName") ,"trend", jo.get("contents") == null ? "": jo.get("contents") ));

		collector.ack(tuple);

	}

    @Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
    	System.out.println( "===============WebCrawlerBolt_Trend declareOutputFields===============");
           // TODO Auto-generated method stub
    	declarer.declare(new Fields("siteName","column","contents"));

    }

	/*
	 * @see com.storm.demo.service.WebCrawlerService#getTrendsContents(java.util.Map)
	 * 사이트별 실검 데이터 가져오기
	 */
	public JSONObject getTrendsContents(String siteName, Document contents)  {

		JSONObject jsonobject = new JSONObject();
		String[] sb;

		// 실시간 검색
		if("naver".equals(siteName)){
			sb = (contents.select("a.link_issue").text()).split(" ");

		}else if("daum".equals(siteName)){
			sb = (contents.select("span.ah_k").text()).split(" ");
		}else if("nate".equals(siteName)){
			sb = (contents.select("span.ah_k").text()).split(" ");
		}else {
			sb = (contents.select("span.keyword.d_keyword").text()).split(" ");
		}

		System.out.println("getTrendsContents siteName : "+siteName);
		System.out.println("getTrendsContents contents : "+sb.toString());

		jsonobject.put("siteName", siteName);
		jsonobject.put("contents", sb.toString());

		System.out.println("getTrendsContents jsonobject : "+jsonobject.toJSONString());

		return jsonobject;
	}

}
