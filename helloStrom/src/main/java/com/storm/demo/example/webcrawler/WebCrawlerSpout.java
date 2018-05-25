package com.storm.demo.example.webcrawler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
		System.out.println( "===============WebCrawlerSpout nextTuple===============");
		JSONArray ja = getRawContents(mapData);
		JSONObject jo;
		UUID msgId = UUID.randomUUID();

		for (int i = 0; i < ja.length(); i++) {

			try {
				jo = ja.getJSONObject(i);

			this.collector.emit(new Values( jo.get("siteName"), jo.get("contents")),msgId);
			System.out.println("3. WebCrawlerSpout nextTuple >>>>>>>>>>>>>>>>>>>>>> "+ jo.get("siteName")+" :"+ msgId);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	  }

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer){
		System.out.println( "===============WebCrawlerSpout declareOutputFields===============");
	     declarer.declare(new Fields("siteName","contents"));
	  }

    @Override
    public void ack(Object msgId) {
    	System.out.println("OK:"+msgId);
    }

	public JSONArray getRawContents(Map<String, Object>  request) {

		JSONArray jsonArray = new JSONArray();
		JSONObject jsonobject  = null;

		try {
			List<String> urlList = (ArrayList<String>)request.get("url");
			for (int i = 0; i < urlList.size(); i++) {

				System.out.println("1. @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ "+ urlList.get(i));
				jsonobject = new JSONObject();
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
				System.out.println("2. getRawContents >>>  jsonobject >>> siteName >>>"+jsonobject.get("siteName"));
				jsonArray.put(jsonobject);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return jsonArray;
	}



}