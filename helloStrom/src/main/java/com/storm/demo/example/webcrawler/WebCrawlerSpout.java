package com.storm.demo.example.webcrawler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.storm.shade.org.json.simple.JSONArray;
import org.apache.storm.shade.org.json.simple.JSONObject;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
		JSONObject jo = getRawContents(mapData);

//		for (int i = 0; i < jsonArray.size(); i++) {
//			JSONObject jo = (JSONObject) jsonArray.get(i);
			this.collector.emit(new Values( jo.get("siteName"), jo.get("contents")));
			System.out.println("> >> WebCrawlerSpout textTuple >>>>>>>>>>>>>>>>>>>>>> "+ jo.get("siteName"));
//		}

	  }

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer){
	     declarer.declare(new Fields("siteName","contents"));
	  }

    @Override
    public void ack(Object msgId) {
    	System.out.println("OK:"+msgId);
    }

	public JSONObject getRawContents(Map<String, Object>  request) {

		JSONArray jsonArray = new JSONArray();
		JSONObject jsonobject = new JSONObject();

		try {
			List<String> urlList = (ArrayList<String>)request.get("url");
			for (int i = 0; i < urlList.size(); i++) {

				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ "+ urlList.get(i));

				String name = "";
				Document contents = null;
				String url = urlList.get(i);
				if("https://www.naver.com".equals(url)){
					name = "naver";
//					contents = getTrends("naver", object.toString()); // 실검
					contents = Jsoup.connect(url).get();
				}else if("https://www.daum.net".equals(url)){
					name = "daum";
//					contents = getTrends("daum", object.toString());// 실검
					contents = Jsoup.connect(url).get();
				}else if("https://www.nate.com".equals(url)){
					name = "nate";
//					contents = getTrends("nate", object.toString()); // 실검
					contents = Jsoup.connect(url).get();
				}else {
					name = "zum";
//					contents = getTrends("zum", object.toString()); // 실검
					contents = Jsoup.connect(url).get();
				}

//				_mapData.put(name,contents);//html의 태그안에 문자열만 가지고 올때 사용
				jsonobject.put("siteName", name);
				jsonobject.put("contents", contents);

			}
//			jsonArray.add(jsonobject);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return jsonobject;
	}


}