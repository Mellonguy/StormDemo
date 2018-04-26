package com.storm.demo.example;

import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;

public class WebCrawlerTopologyLocal {

	private String spout = "url";
	private String bolt = "word";

	protected StormTopology WebCrawlerTopology(WebCrawlerSpout webCrawlerSpout) {
		System.out.println("WebCrawlerTopology START >>>>>>>>>>>>>>>>>>>.");
		TopologyBuilder topologyBuilder = new TopologyBuilder();
		topologyBuilder.setSpout(spout, webCrawlerSpout,4);
		topologyBuilder.setBolt(bolt,new WebCrawlerBolt(),8).localOrShuffleGrouping(spout);

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
