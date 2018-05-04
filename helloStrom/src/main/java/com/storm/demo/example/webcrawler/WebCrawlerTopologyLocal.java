package com.storm.demo.example.webcrawler;

import java.util.List;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.Utils;

import com.storm.demo.example.wordcount.ReportBolt;
import com.storm.demo.props.StormProps;

public class WebCrawlerTopologyLocal {

	private String spout = "crawlerSpout";
	private String bolt = "crawlerBolt";
	private String countBolt = "crawlerCountBolt";
    private String REPORT_BOLT_ID = "report-bolt";

	public TopologyBuilder webCrawlerTopologyLocal(StormProps stormProps , List<Object> _list ) {
		System.out.println("WebCrawlerTopologyLocal START >>>>>>>>>>>>>>>>>>>.");

		TopologyBuilder topologyBuilder = new TopologyBuilder();
		Config config = new Config();
		config.setDebug(true);
		config.setMaxTaskParallelism(3);
//		config.setNumWorkers(3);

		topologyBuilder.setSpout(spout, new WebCrawlerSpout(_list));
		topologyBuilder.setBolt(bolt, new WebCrawlerBolt()).shuffleGrouping(spout);
		topologyBuilder.setBolt(countBolt, new WebCrawlerWordCountBolt()).fieldsGrouping(bolt, new Fields("word"));
		topologyBuilder.setBolt(REPORT_BOLT_ID, new ReportBolt()).globalGrouping(countBolt); // CountBolt -> ReportBolt

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(stormProps.getTopologyName(), config, topologyBuilder.createTopology());
        Utils.sleep(5000);
        cluster.killTopology(stormProps.getTopologyName());
        cluster.shutdown();
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
