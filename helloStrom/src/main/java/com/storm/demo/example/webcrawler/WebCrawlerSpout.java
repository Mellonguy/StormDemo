package com.storm.demo.example.webcrawler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
		JSONArray jsonArray = getRawContents(mapData);

		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jo = (JSONObject) jsonArray.get(i);
			this.collector.emit(new Values( jo.get("stieName"), jo.get("contents")));
		}

	  }

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer){
	     declarer.declare(new Fields("siteName","contents"));
	  }

    @Override
    public void ack(Object msgId) {
    	System.out.println("OK:"+msgId);
    }

	public JSONArray getRawContents(Map request) {

		Document _doc;
		StringBuffer docSB;
		Map<String,Object> _mapData = null;
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonobject = new JSONObject();

		try {
			ArrayList urlList = (ArrayList) request.get("url");
			docSB = new StringBuffer();
			_mapData = new HashMap<String, Object>();
//			for (Iterator iterator = urlList.iterator(); iterator.hasNext();) {
//				Object object = iterator.next();
////				_doc =  Jsoup.connect(object.toString()).get();

				String name = "";
				Object contents;

//				Elements e1 = _doc.select("div.ah_list.PM_CL_realtimeKeyword_list_base"); // selector를 이용하여 가져온다
//				docSB.append(e1.text()); //html의 태그안에 문자열만 가지고 올때 사용
				if("https://www.naver.com".equals(urlList.get(0).toString())){
					name = "naver";
//					contents = getTrends("naver", object.toString()); // 실검
					contents = Jsoup.connect(urlList.get(0).toString()).get();
				}else if("https://www.daum.net".equals(urlList.get(0).toString())){
					name = "daum";
//					contents = getTrends("daum", object.toString());// 실검
					contents = Jsoup.connect(urlList.get(0).toString()).get();
				}else if("https://www.nate.com".equals(urlList.get(0).toString())){
					name = "nate";
//					contents = getTrends("nate", object.toString()); // 실검
					contents = Jsoup.connect(urlList.get(0).toString()).get();
				}else {
					name = "zum";
//					contents = getTrends("zum", object.toString()); // 실검
					contents = Jsoup.connect(urlList.get(0).toString()).get();
				}

//				_mapData.put(name,contents);//html의 태그안에 문자열만 가지고 올때 사용
				jsonobject.put("siteName", name);
				jsonobject.put("contents", contents);

				System.out.println("> >> jsonobject >> "+ jsonobject.get("siteName"));
//				}

			jsonArray.add(jsonobject);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return jsonArray;
	}


}