package com.storm.demo.service;

import java.io.IOException;
import java.util.Map;

import org.apache.storm.shade.org.json.simple.JSONArray;
import org.apache.storm.shade.org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class WebCrawlerServiceImpl implements WebCrawlerService {

	/*
	 * @see com.storm.demo.service.WebCrawlerService#getRawContents(java.util.Map)
	 */
	@Override
	public JSONArray getRawContents(Map request) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * @see com.storm.demo.service.WebCrawlerService#getTrendsContents(org.json.JSONObject)
	 */
	@Override
	public JSONObject getTrendsContents(JSONObject request)  {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * @see com.storm.demo.service.WebCrawlerService#getNewsContents(org.json.JSONObject)
	 */
	@Override
	public JSONObject getNewsContents(JSONObject request) throws  IOException {
		// TODO Auto-generated method stub
		return null;
	}

//	/*
//	 * @see com.storm.demo.service.WebCrawlerService#getRqwContents(java.util.Map)
//	 * 사이트별 RAW 데이터 가져오기
//	 */
//	@Override
//	public  JSONArray getRawContents(Map request) {
//
//		Document _doc;
//		StringBuffer docSB;
//		Map<String,Object> _mapData = null;
//		JSONArray jsonArray = new JSONArray();
//		JSONObject jsonobject = new JSONObject();
//
//		try {
//			ArrayList urlList = (ArrayList) request.get("url");
//			docSB = new StringBuffer();
//			_mapData = new HashMap<String, Object>();
//			for (Iterator iterator = urlList.iterator(); iterator.hasNext();) {
//				Object object = iterator.next();
////				_doc =  Jsoup.connect(object.toString()).get();
//
//				String name = "";
//				Object contents;
//
////				Elements e1 = _doc.select("div.ah_list.PM_CL_realtimeKeyword_list_base"); // selector를 이용하여 가져온다
////				docSB.append(e1.text()); //html의 태그안에 문자열만 가지고 올때 사용
//				if("https://www.naver.com".equals(object)){
//					name = "naver";
////					contents = getTrends("naver", object.toString()); // 실검
//					contents = Jsoup.connect(object.toString()).get();
//				}else if("https://www.daum.net".equals(object)){
//					name = "daum";
////					contents = getTrends("daum", object.toString());// 실검
//					contents = Jsoup.connect(object.toString()).get();
//				}else if("https://www.nate.com".equals(object)){
//					name = "nate";
////					contents = getTrends("nate", object.toString()); // 실검
//					contents = Jsoup.connect(object.toString()).get();
//				}else {
//					name = "zum";
////					contents = getTrends("zum", object.toString()); // 실검
//					contents = Jsoup.connect(object.toString()).get();
//				}
//
////				_mapData.put(name,contents);//html의 태그안에 문자열만 가지고 올때 사용
//				jsonobject.put("siteName", name);
//				jsonobject.put("contents", contents);
//				}
//
//			jsonArray.put(jsonobject);
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//
//		return jsonArray;
//	}
//
//	/*
//	 * @see com.storm.demo.service.WebCrawlerService#getTrendsContents(java.util.Map)
//	 * 사이트별 실검 데이터 가져오기
//	 */
//	@Override
//	public JSONObject getTrendsContents(JSONObject request) throws JSONException {
//
//
//		String siteName =request.getString("siteName");
//		Document data = null;
//		JSONObject jsonobject = new JSONObject();
//		data = (Document)request.get("contents");
//		String[] sb;
//
//		// 실시간 검색
//		if("naver".equals(siteName)){
//			sb = (data.select("a.link_issue").text()).split(" ");
//
//		}else if("daum".equals(siteName)){
//			sb = (data.select("span.ah_k").text()).split(" ");
//		}else if("nate".equals(siteName)){
//			sb = (data.select("span.ah_k").text()).split(" ");
//		}else {
//			sb = (data.select("span.keyword.d_keyword").text()).split(" ");
//		}
//
//		jsonobject.put("siteName", siteName);
//		jsonobject.put("category", "trend");
//		jsonobject.put("contents", sb.toString());
//
//		return jsonobject;
//	}
//
//	/*
//	 * @see com.storm.demo.service.WebCrawlerService#getNewsContents(java.util.Map)
//	 * 사이트별 뉴스데이터 가져오기
//	 */
//	@Override
//	public JSONObject getNewsContents(JSONObject request) throws JSONException, IOException {
//
//		String siteName =request.getString("name");
//		Document data = null;
//		JSONObject jsonobject = new JSONObject();
//		data = (Document)request.get("contents");
//
//		// 뉴스 검색
//		if("naver".equals(siteName)){
//			data = Jsoup.connect("http://news.naver.com").get();;
//		}else if("daum".equals(siteName)){
//			data = Jsoup.connect("http://media.daum.net").get();;
//		}else if("nate".equals(siteName)){
//			data = Jsoup.connect("http://news.nate.com").get();;
//		}else {
//			data = Jsoup.connect("http://news.zum.com\"").get();;
//		}
//
//		jsonobject.put("siteName", siteName);
//		jsonobject.put("category", "news");
//		jsonobject.put("contents", data);
//
//		return jsonobject;
//
//	}




}
