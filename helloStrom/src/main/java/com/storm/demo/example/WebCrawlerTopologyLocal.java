package com.storm.demo.example;

import org.apache.storm.topology.TopologyBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import com.storm.demo.props.StormProps;

public class WebCrawlerTopologyLocal {

	@Autowired
	private WebCrawlerSpout webCrawlerSpout;

	 @Autowired
    private StormProps stormProps;

	@Autowired
	private WebCrawlerBolt webCrawlerBolt;


	private String spout = "crawlerSpout";
	private String bolt = "crawlerBolt";

	protected TopologyBuilder buildTopology() {
		System.out.println("WebCrawlerTopologyLocal START >>>>>>>>>>>>>>>>>>>.");
//		TopologyBuilder topologyBuilder = new TopologyBuilder();
//		Config conf = new Config();
//		conf.setNumWorkers(stormProps.getTopologyWorkers());
//        conf.setMaxSpoutPending(stormProps.getTopologyMaxSpoutPending());
//
//		topologyBuilder.setSpout(spout, webCrawlerSpout,4);
//		topologyBuilder.setBolt(bolt,webCrawlerBolt,8).localOrShuffleGrouping(spout);
//		LocalCluster cluster = new LocalCluster();
//		cluster.submitTopology(stormProps.getTopologyName(), conf, topologyBuilder.createTopology());
		return null;

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
