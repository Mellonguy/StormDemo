/*
 * Copyright ⓒ [2018] KTH corp.All rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */
package com.storm.demo.example.hello;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.utils.Utils;

public class HelloTopologyLocal {

//	 public static void main(String args[]){
	public void helloTopologyLocal() {
       TopologyBuilder builder = new TopologyBuilder();
       builder.setSpout("HelloSpout", new HelloSpout(),2);
       builder.setBolt("HelloBolt", new HelloBolt(),4).shuffleGrouping("HelloSpout");

       Config conf = new Config();
       conf.setDebug(true);
       LocalCluster cluster = new LocalCluster();

       cluster.submitTopology("HelloTopologyLocal", conf,builder.createTopology());
       Utils.sleep(10000);
       // kill the LearningStormTopology
       cluster.killTopology("HelloTopologyLocal");
       // shutdown the storm test cluster
       cluster.shutdown();

	}


}


