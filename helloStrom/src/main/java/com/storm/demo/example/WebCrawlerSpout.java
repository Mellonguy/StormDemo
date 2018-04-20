package com.storm.demo.example;
import java.util.List;
import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.springframework.beans.factory.annotation.Autowired;

import com.storm.demo.service.WebCrawlerService;

public class WebCrawlerSpout extends BaseRichSpout {
          private static final long serialVersionUID = 1L;
          private SpoutOutputCollector collector;
          List<String> _list;
          Map<String, Object> _map;
          Document _doc;

          /**
		 *
		 */
		public WebCrawlerSpout(Map<String, Object> requestParam) {

		}
          @Autowired
          private WebCrawlerService webCrawlerService;

          @Override
		public void open(Map conf,TopologyContext context,SpoutOutputCollector collector){
               this.collector = collector;
          }

          @Override
		public void nextTuple(){
        	  String data = webCrawlerService.crawlerDataIgnoleHttpString(dataSet);
        	  System.out.println("Spout start  >>>>>>>>>>");

                 this.collector.emit(new Values("hello world"));
          }

          @Override
		public void declareOutputFields(OutputFieldsDeclarer declarer){
                 declarer.declare(new Fields("say"));
          }

}