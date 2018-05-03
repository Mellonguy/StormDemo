package com.storm.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.storm.demo.props.StormProps;

@Service
public class WebCrawlerServiceImpl implements WebCrawlerService {

    @Autowired
    private StormProps stormProps;

	private String spout = "crawlerSpout";
	private String bolt = "crawlerBolt";
	private Map<String, Object> _map;


	/*TEST로 하다가 나중에 지우자 return type르 바꾸
	 * @see com.storm.demo.service.WebCrawlerService#crawlerDataIgnoleHttpString(java.util.Map)
	 */
	@Override
	public String crawlerDataIgnoleHttpString(Map<String, Object> dataSet) throws AlreadyAliveException, InvalidTopologyException, AuthorizationException, IllegalArgumentException, IOException{
		System.out.println("WebCrawlerServiceImpl crawlerDataIgnoleHttpString >>>>>>>>>>>>>>>>>>>> START");


        System.out.println("WebCrawlerServiceImpl crawlerDataIgnoleHttpString >>>>>>>>>>>>>>>>>>>>END");
		return "";
	}

	/*
	 * @see com.storm.demo.service.WebCrawlerService#crawlerDataIgnoleHttpMap()
	 */
	@Override
	public List<Object> crawlerDataIgnoleHttpMap(Map args) throws ClientProtocolException, IOException {
		Document _doc;
		StringBuffer docSB;
		List<Object> _listA = null;
		try {
			ArrayList urlList = (ArrayList) args.get("url");
			docSB = new StringBuffer();
			_listA = new ArrayList<Object>();
			for (Iterator iterator = urlList.iterator(); iterator.hasNext();) {
				Object object = iterator.next();
				_doc =  Jsoup.connect(object.toString()).get();
				docSB.append(_doc.text());
				_listA.add(docSB);
				}


		} catch (Exception e) {
			// TODO: handle exception
		}

		return _listA;
	}

	/*
	 * @see com.storm.demo.service.WebCrawlerService#getCurrentData()
	 */
	@Override
	public String getCurrentData() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
		return sdf.format(new Date());
	}

	/*
	 * @see com.storm.demo.service.WebCrawlerService#crawlerDataWithHttpString()
	 */
	@Override
	public String crawlerDataWithHttpString(Map<String,String> args) throws ClientProtocolException, IOException {

		String url = args.get("url");

		//가져올 http 주소 셋팅
		HttpPost http = new HttpPost(url);

		//가져오기를 실행할 클라이언트 객체 생성
		HttpClient httpClient = HttpClientBuilder.create().build();

		//실행 및 실행 데이터를 Response 객체에 담음
		HttpResponse response = httpClient.execute(http);

		//Response 받은 데이터 중, DOM 데이터를 가져와 Entity에 담음
		HttpEntity entity = response.getEntity();

		//Charset	을 알아내기위해 DOM의 컨텐트 타입을 가져와 담고 Charset을 가져옴
		ContentType contentType = ContentType.getOrDefault(entity);
		Charset charset = contentType.getCharset();

		//DOM 데이터를 한 줄씩 읽기위해 Reader에 담음(InputStream / Bufferd 중 선택은 개인취향)
		BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), charset));

		//가져온 DOM 데이터를 담기위한 그릇
		StringBuffer sb = new StringBuffer();

		//DOM 데이터 가져오기
		String line = "";
		while((line=br.readLine()) != null){
			sb.append(line+"\n");
		}

		return sb.toString();
	}

	/*
	 * @see com.storm.demo.service.WebCrawlerService#crawlerDataIgnoleHttpString()
	 */
	@Override
	public Document crawlerDataIgnoleHttpDocument(Map<String,String> args) throws ClientProtocolException, IOException {

		String url = args.get("url");

		//Jsoup으로 파싱해보자.
		Document doc = Jsoup.connect(url).get();
		return doc;
	}

	/*
	 * @see com.storm.demo.service.WebCrawlerService#crawlerDataIgnoleHttpLsit()
	 */
	@Override
	public List<String> crawlerDataIgnoleHttpLsit(Map args) throws ClientProtocolException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * @see com.storm.demo.service.WebCrawlerService#crawlerDataIgnoleHttpMap()
	 */
//	@Override
//	public Map<String, String> crawlerDataIgnoleHttpMap(Map args) throws ClientProtocolException, IOException {
//
//		try {
//			String url = String.valueOf(_map.get("url"));
//			ArrayList urlList = (ArrayList) _map.get("url");
//			docSB = new StringBuffer();
//			_list = new ArrayList<Object>();
//			for (Iterator iterator = urlList.iterator(); iterator.hasNext();) {
//				Object object = iterator.next();
//				_doc =  Jsoup.connect(object.toString()).get();
//				docSB.append(_doc.text());
//				_list.add(docSB);
//				}
//
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//
//		return null;
//	}


	/*
	 * @see com.storm.demo.service.WebCrawlerService#getToString()
	 */
	@Override
	public String getToString() {
		// TODO Auto-generated method stub
		return null;
	}

}
