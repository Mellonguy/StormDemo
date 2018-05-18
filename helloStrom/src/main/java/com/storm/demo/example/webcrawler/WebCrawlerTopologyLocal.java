package com.storm.demo.example.webcrawler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.utils.Utils;

import com.storm.demo.props.StormProps;

public class WebCrawlerTopologyLocal {

	private String spout = "crawlerSpout";

	private String boltTrend = "boltTrend";
	private String boltNews = "boltNews";

	private String boltSearchWord = "boltSearchWord";


	private String boltSMS = "crawlerBolt_SMS";
	private String boltEMAIL = "crawlerBolt_EMAIL";
	private String boltARS = "crawlerBolt_ARS";



	private String countBolt = "crawlerCountBolt";
    private String REPORT_BOLT_ID = "report-bolt";

    private int sleepTime = 1;
	public TopologyBuilder webCrawlerTopologyLocal(StormProps stormProps ,  Map<String, Object> requestDataSet) {

		int tickTupleFreqSecs = 5;

		TopologyBuilder topologyBuilder = new TopologyBuilder();
		Config config = new Config();
		config.setDebug(false);
//		config.put(Config.TOPOLOGY_SUBPROCESS_TIMEOUT_SECS, tickTupleFreqSecs);
//		config.setMaxSpoutPending(1000);
		// Spout 시작
		System.out.println("1. webCrawlerTopologyLocal spout 시작 ===========>");
		topologyBuilder.setSpout(spout, new WebCrawlerSpout(requestDataSet),2);


		List<String> columnList = (ArrayList<String>) requestDataSet.get("Column");

		for (int i = 0; i < columnList.size(); i++) {
			// 실검/ 뉴스여부에 따라
			// 실검
			if("trend".equals(columnList.get(i))) {
				System.out.println("2-1. webCrawlerTopologyLocal 트렌드 bolt 시작 ===========>");
				topologyBuilder.setBolt(boltTrend, new WebCrawlerBolt_Trend(),4).shuffleGrouping(spout);

				System.out.println("3-1. webCrawlerTopologyLocal 서치 트렌드  워드 bolt 시작 ===========>");
				topologyBuilder.setBolt(boltSearchWord, new WebCrawlerBoltSearchWord(requestDataSet),1).shuffleGrouping(boltTrend);
			}else if("news".equals(columnList.get(i))) {
				System.out.println("2-2. webCrawlerTopologyLocal 뉴스  bolt 시작 ===========>");
				topologyBuilder.setBolt(boltNews, new WebCrawlerBolt_News(),4).shuffleGrouping(spout);

				System.out.println("3-2. webCrawlerTopologyLocal 서치 뉴스  워드 bolt 시작 ===========>");
				topologyBuilder.setBolt(boltSearchWord, new WebCrawlerBoltSearchWord(requestDataSet),1).shuffleGrouping(boltNews);
			}

		}

		//알람방법 여부에 따라
		List<String> alaramList = (ArrayList<String>) requestDataSet.get("alarm");
		for (int i = 0; i < alaramList.size(); i++) {
			// 알람리스트
			if("sms".equals(alaramList.get(i))) {
				System.out.println("4-1. webCrawlerTopologyLocal 알람 SMS bolt 시작 ===========>");
				topologyBuilder.setBolt(boltSMS, new WebCrawlerBolt_SMS(),8).shuffleGrouping(boltSearchWord);
//				topologyBuilder.setBolt(boltSMS+"2", new WebCrawlerBolt_SMS(),1).shuffleGrouping("boltSearchWord2");
			}else if("mail".equals(alaramList.get(i))) {
				System.out.println("4-2. webCrawlerTopologyLocal 알람 Email bolt 시작 ===========>");
				topologyBuilder.setBolt(boltEMAIL, new WebCrawlerBolt_Email(),8).shuffleGrouping(boltSearchWord);
			}else {
				System.out.println("4-3. webCrawlerTopologyLocal 알람  ARS bolt 시작 ===========>");
				topologyBuilder.setBolt(boltARS, new WebCrawlerBolt_Ars(),8).shuffleGrouping(boltSearchWord);
			}

			}
//		topologyBuilder.setBolt(countBolt, new WebCrawlerWordCountBolt()).fieldsGrouping(bolt, new Fields("keyword"));



		if(requestDataSet.get("s_t") != null && requestDataSet.get("s_t") != "") {
			sleepTime = Integer.parseInt((String) requestDataSet.get("s_t"));

		}else {
			sleepTime *= 10;
		}




//		topologyBuilder.setBolt(REPORT_BOLT_ID, new ReportBolt()).globalGrouping(countBolt); // CountBolt -> ReportBolt

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(stormProps.getTopologyName(), config, topologyBuilder.createTopology());
        Utils.sleep(1000);
        try { Thread.sleep(1000*sleepTime); } catch (InterruptedException e) { } // waiting 10s
        cluster.killTopology(stormProps.getTopologyName());
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
