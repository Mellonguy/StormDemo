package com.storm.demo.example.webcrawler;

import java.util.List;
import java.util.Map;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.Utils;

import com.storm.demo.example.wordcount.ReportBolt;
import com.storm.demo.props.StormProps;

public class WebCrawlerTopologyLocal {

	private String spout = "crawlerSpout";

	private String boltNaver = "crawlerBolt_Naver";
	private String boltDaum = "crawlerBolt_Daum";
	private String boltZum = "crawlerBolt_Zum";
	private String boltNate = "crawlerBolt_Nate";

	private String boltSMS = "crawlerBolt_SMS";
	private String boltEMAIL = "crawlerBolt_EMAIL";
	private String boltARS = "crawlerBolt_ARS";

	private String bolt = "crawlerBolt";

	private String countBolt = "crawlerCountBolt";
    private String REPORT_BOLT_ID = "report-bolt";
    private int sleepTime = 1000;
	public TopologyBuilder webCrawlerTopologyLocal(StormProps stormProps , List<Object> _list, Map<String, Object> requestDataSet) {
		System.out.println("WebCrawlerTopologyLocal START >>>>>>>>>>>>>>>>>>>.");

		TopologyBuilder topologyBuilder = new TopologyBuilder();
		Config config = new Config();
		config.setDebug(true);
		config.setMaxTaskParallelism(3);
//		config.setNumWorkers(3);

		topologyBuilder.setSpout(spout, new WebCrawlerSpout(_list));
//		topologyBuilder.setBolt(bolt, new WebCrawlerBolt(requestDataSet)).shuffleGrouping(spout);
//		topologyBuilder.setBolt(countBolt, new WebCrawlerWordCountBolt()).fieldsGrouping(bolt, new Fields("keyword"));


		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+requestDataSet.get("s_t"));

		if(requestDataSet.get("s_t") != null && requestDataSet.get("s_t") != "") {
			sleepTime = Integer.parseInt((String) requestDataSet.get("s_t"));

		}else {
			sleepTime *= 10;
		}


		/**
		 * 사이트 : naver , daum , nate, zum
		 * 키워드 :
		 * 알람 : sms, email, ars
		 *
		 * spout |- naverBolt  -|
		 *       |              |- SMS  -|
		 *       |-  daumBolt  -|        |
		 *       |              |- Emai -|
		 *       |-  nateBolt  -|        |
		 *       |              |- ARS  _|
		 *       |-  zumBolt   -|
		 *                      |- wordCountBolt
		 */


		List<String> _listUrl = (List<String>) requestDataSet.get("url");
		for (String listUrl : _listUrl) {
			if("https://www.naver.com".equals(listUrl)){
				topologyBuilder.setBolt(boltNaver, new WebCrawlerBolt_Naver(requestDataSet)).shuffleGrouping(spout);
				topologyBuilder.setBolt(countBolt, new WebCrawlerWordCountBolt()).fieldsGrouping(boltNaver, new Fields("word"));
			}else if("https://www.daum.net".equals(listUrl)){
				topologyBuilder.setBolt(boltDaum, new WebCrawlerBolt_Daum(requestDataSet)).shuffleGrouping(spout);
				topologyBuilder.setBolt(countBolt, new WebCrawlerWordCountBolt()).fieldsGrouping(boltDaum, new Fields("word"));
			}else if("https://www.nate.com".equals(listUrl)){
				topologyBuilder.setBolt(boltNate, new WebCrawlerBolt_Nate(requestDataSet)).shuffleGrouping(spout);
				topologyBuilder.setBolt(countBolt, new WebCrawlerWordCountBolt()).fieldsGrouping(boltNate, new Fields("word"));
			}else {
				topologyBuilder.setBolt(boltZum, new WebCrawlerBolt_Zum(requestDataSet)).shuffleGrouping(spout);
				topologyBuilder.setBolt(countBolt, new WebCrawlerWordCountBolt()).fieldsGrouping(boltZum, new Fields("word"));
			}
		}

		List<String> _listAlarm = (List<String>) requestDataSet.get("alarm");
		for (String listUrl : _listAlarm) {
			if("sms".equals(listUrl)){
				topologyBuilder.setBolt(boltSMS, new WebCrawlerBolt_SMS(requestDataSet)).shuffleGrouping(spout);
			}else if("email".equals(listUrl)){
				topologyBuilder.setBolt(boltEMAIL, new WebCrawlerBolt_Ars(requestDataSet)).shuffleGrouping(spout);
			}else {
				topologyBuilder.setBolt(boltARS, new WebCrawlerBolt_Email(requestDataSet)).shuffleGrouping(spout);
			}
		}

//		topologyBuilder.setBolt(countBolt, new WebCrawlerWordCountBolt()).fieldsGrouping(bolt, new Fields("word"));
		topologyBuilder.setBolt(REPORT_BOLT_ID, new ReportBolt()).globalGrouping(countBolt); // CountBolt -> ReportBolt

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(stormProps.getTopologyName(), config, topologyBuilder.createTopology());
        Utils.sleep(sleepTime);
//        cluster.killTopology(stormProps.getTopologyName());
//        cluster.shutdown();
		return topologyBuilder;

	}
/*        public static void main(String args[]){
               TopologyBuilder builder = new TopologyBuilder();
               builder.setSpout("WebCrawlerSpout", spout,4);
               builder.setBolt("WebCrawlerBolt", new WebCrawlerBolt(),8).shuffleGrouping("HelloSpout");

               Config conf = new Config();
               conf.setDebug(true);
               LocalCluster cluster = new LocalCluster();

               cluster.submitTopology("WebCrawlerTopologyLocal", conf,builder.createTopology());
               Utils.sleep(10000);
               // kill the LearningStormTopology
               cluster.killTopology("HelloTopologyLocal");
               // shutdown the storm test cluster
               cluster.shutdown();
        }
*/
}
