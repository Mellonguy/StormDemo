package com.storm.demo.example;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.utils.Utils;

public class WebCrawlerTopologyLocal {
        public static void main(String args[]){

               TopologyBuilder builder = new TopologyBuilder();
               builder.setSpout("WebCrawlerSpout", new WebCrawlerSpout(),4);
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

}
