package com.storm.demo.example;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;

import com.storm.demo.service.WebCrawlerService;

public class WebCrawlerSpout extends BaseRichSpout {

	@Autowired
    private WebCrawlerService webCrawlerService;

	private static final long serialVersionUID = 1L;
	private SpoutOutputCollector collector;
	private List<Object> _list;
	private Map<String, Object> _map;
	private Document _doc;

      /**
     * @throws IOException
	 *
	 */
	public WebCrawlerSpout(Map<String, Object> requestParam) throws IOException {
		this._map = requestParam;
		String url = String.valueOf(_map.get("url"));
		ArrayList urlList = (ArrayList) _map.get("url");
		StringBuffer docSB = new StringBuffer();
		_list = new ArrayList<Object>();
		for (Iterator iterator = urlList.iterator(); iterator.hasNext();) {
			Object object = iterator.next();
			_doc =  Jsoup.connect(object.toString()).get();
			docSB.append(_doc.text());
			_list.add(docSB);
			}
		System.out.println("WebCrawlerSpout  StringBuffer>>>>>>>>>>>>>>> :"+docSB.toString());


	}


	@Override
	public void open(Map conf,TopologyContext context,SpoutOutputCollector collector){
	       this.collector = collector;
	  }

	@Override
	public void nextTuple(){
		System.out.println("WebCrawlerSpout.nextTuple() >>>>>>>>>  >>>>>>>.. START");
		System.out.println("Spout start  >>>>>>>>>>");
		this.collector.emit(_list);
	  }

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer){
	     declarer.declare(new Fields("url"));
	  }

}