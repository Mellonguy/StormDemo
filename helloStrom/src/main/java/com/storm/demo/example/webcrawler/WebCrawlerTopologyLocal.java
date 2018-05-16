package com.storm.demo.example.webcrawler;

import java.util.Map;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.utils.Utils;

import com.storm.demo.props.StormProps;

public class WebCrawlerTopologyLocal {

	private String spout = "crawlerSpout";

	private String boltTrend = "bolt_trend";
	private String boltNews = "bolt_news";

	private String boltPortal = "crawlerBolt_Portal";


	private String boltSMS = "crawlerBolt_SMS";
	private String boltEMAIL = "crawlerBolt_EMAIL";
	private String boltARS = "crawlerBolt_ARS";



	private String countBolt = "crawlerCountBolt";
    private String REPORT_BOLT_ID = "report-bolt";

    private int sleepTime = 1000;
	public TopologyBuilder webCrawlerTopologyLocal(StormProps stormProps ,  Map<String, Object> requestDataSet) {
		System.out.println("WebCrawlerTopologyLocal START >>>>>>>>>>>>>>>>>>>.");

		TopologyBuilder topologyBuilder = new TopologyBuilder();
		Config config = new Config();
		config.setDebug(false);
//		config.setMaxTaskParallelism(3);
//		config.setNumWorkers(3);

		// Spout 설
		topologyBuilder.setSpout(spout, new WebCrawlerSpout(requestDataSet),3);


		// 실검/ 뉴스여부에 따라
		// 실검
		if("trend".equals(requestDataSet.get("Column"))) {
			topologyBuilder.setBolt(boltTrend, new WebCrawlerBolt_Trend(requestDataSet)).shuffleGrouping(spout);
		}

		// 뉴스
		if("news".equals(requestDataSet.get("Column"))) {
			topologyBuilder.setBolt(boltNews, new WebCrawlerBolt_News(requestDataSet)).shuffleGrouping(spout);
		}

		//알람방법 여부에 따




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
		 *  1 --->    2     ---->  3
		 * spout |- naverBolt  -|
		 *       |              |- SMS   -|
		 *       |-  daumBolt  -|         |
		 *       |              |- Email -|
		 *       |-  nateBolt  -|         |
		 *       |              |- ARS   -|
		 *       |-  zumBolt   -|
		 *                      |- wordCount
		 */


		// Bolt 설정 1. potal 수집
		topologyBuilder.setBolt(boltPortal, new WebCrawlerBolt_Trend(requestDataSet),6).shuffleGrouping(spout);


		// Bolt 설정 2. 알람 방법 수집
//		topologyBuilder.setBolt(boltNaver, new WebCrawlerBolt_Naver(requestDataSet)).fieldsGrouping(boltPortal, new Fields("siteName"));


//		List<String> _listAlarm = (List<String>) requestDataSet.get("alarm");
//		for (String listUrl : _listAlarm) {
//			if("sms".equals(listUrl)){
//				topologyBuilder.setBolt(boltSMS, new WebCrawlerBolt_SMS(requestDataSet)).shuffleGrouping(spout);
//			}else if("email".equals(listUrl)){
//				topologyBuilder.setBolt(boltEMAIL, new WebCrawlerBolt_Email(requestDataSet)).shuffleGrouping(spout);
//			}else {
//				topologyBuilder.setBolt(boltARS, new WebCrawlerBolt_Ars(requestDataSet)).shuffleGrouping(spout);
//			}
//		}


//		topologyBuilder.setBolt(REPORT_BOLT_ID, new ReportBolt()).globalGrouping(countBolt); // CountBolt -> ReportBolt

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
