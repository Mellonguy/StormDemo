/*
 * Copyright ⓒ [2018] KTH corp.All rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */
package com.storm.demo.example.wordcount;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

public class WordCountTopology {
    private static final String SENTENCE_SPOUT_ID = "sentence-spout";
    private static final String SPLIT_BOLT_ID = "split-bolt";
    private static final String COUNT_BOLT_ID = "count-bolt";
    private static final String REPORT_BOLT_ID = "report-bolt";
    private static final String TOPOLOGY_NAME = "word-count-topology";

    public static void wordCountTopology(){
        SentenceSpout spout = new SentenceSpout();
        SplitBolt splitBolt = new SplitBolt();
        WordCountBolt countBolt = new WordCountBolt();
        ReportBolt reportBolt = new ReportBolt();

        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout(SENTENCE_SPOUT_ID, spout); // Spout 연결
        builder.setBolt(SPLIT_BOLT_ID, splitBolt).shuffleGrouping(SENTENCE_SPOUT_ID); // Spout -> SplitBolt
        builder.setBolt(COUNT_BOLT_ID, countBolt).fieldsGrouping(SPLIT_BOLT_ID, new Fields("word")); // SplitBolt -> CountBolt
        builder.setBolt(REPORT_BOLT_ID, reportBolt).globalGrouping(COUNT_BOLT_ID); // CountBolt -> ReportBolt

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(TOPOLOGY_NAME, new Config(), builder.createTopology());

        try { Thread.sleep(1000 * 10); } catch (InterruptedException e) { } // waiting 10s
        cluster.killTopology(TOPOLOGY_NAME);
        cluster.shutdown();
    }
    
//    public static void main(String[] args) {
//        SentenceSpout spout = new SentenceSpout();
//        SplitBolt splitBolt = new SplitBolt();
//        WordCountBolt countBolt = new WordCountBolt();
//        ReportBolt reportBolt = new ReportBolt();
//
//        TopologyBuilder builder = new TopologyBuilder();
//
//        builder.setSpout(SENTENCE_SPOUT_ID, spout); // Spout 연결
//        builder.setBolt(SPLIT_BOLT_ID, splitBolt).shuffleGrouping(SENTENCE_SPOUT_ID); // Spout -> SplitBolt
//        builder.setBolt(COUNT_BOLT_ID, countBolt).fieldsGrouping(SPLIT_BOLT_ID, new Fields("word")); // SplitBolt -> CountBolt
//        builder.setBolt(REPORT_BOLT_ID, reportBolt).globalGrouping(COUNT_BOLT_ID); // CountBolt -> ReportBolt
//
//        LocalCluster cluster = new LocalCluster();
//        cluster.submitTopology(TOPOLOGY_NAME, new Config(), builder.createTopology());
//
//        try { Thread.sleep(1000 * 10); } catch (InterruptedException e) { } // waiting 10s
//        cluster.killTopology(TOPOLOGY_NAME);
//        cluster.shutdown();
//    }
}
