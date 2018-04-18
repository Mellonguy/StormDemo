/*
 * Copyright ⓒ [2018] KTH corp.All rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */
package com.storm.demo.example;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/**
 * CallLogReaderTopology.java
 *
 * @author Kwon Ki Tae
 * @version 1.0.0
 * @see
 * @since 1.0
 */
public class CallLogReaderTopology {
	private static final String SPOUT = "fakeCallLogReaderSpout";
	private static final String BOLT_LOG = "bolt_log";
	private static final String BOLT_COUNT = "bolt_count";

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		Config config = new Config();
		config.setDebug(true);
		config.setMessageTimeoutSecs(30);

		FakeCallLogReaderSpout fakeCallLogReaderSpout = new FakeCallLogReaderSpout();
		CallLogCreatorBolt callLogCreatorBolt = new CallLogCreatorBolt();
		CallCounterBolt callCounterBolt = new CallCounterBolt();

		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout(SPOUT, fakeCallLogReaderSpout); //spout 연결
		builder.setBolt(BOLT_LOG, callLogCreatorBolt).shuffleGrouping(SPOUT); // SPOUT와 count bolt연결
		builder.setBolt(BOLT_COUNT, callCounterBolt).fieldsGrouping(BOLT_LOG, new Fields("call"));

		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("CallreaderTopology", config, builder.createTopology());
		Thread.sleep(10000);

		cluster.shutdown();
	}

}
