package com.storm.demo.props;

import java.util.Map;

import org.springframework.context.annotation.Configuration;


@Configuration
public class StormProps {

    private String topologyName = "WebCrawlerTopologyLocal" ;

    /**
	 * @return the topologyName
	 */
	public String getTopologyName() {
		return topologyName;
	}

	/**
	 * @param topologyName the topologyName to set
	 */
	public void setTopologyName(String topologyName) {
		this.topologyName = topologyName;
	}

	/**
	 * @return the topologyWorkers
	 */
	public Integer getTopologyWorkers() {
		return topologyWorkers;
	}

	/**
	 * @param topologyWorkers the topologyWorkers to set
	 */
	public void setTopologyWorkers(Integer topologyWorkers) {
		this.topologyWorkers = topologyWorkers;
	}

	/**
	 * @return the numTasks
	 */
	public Integer getNumTasks() {
		return numTasks;
	}

	/**
	 * @param numTasks the numTasks to set
	 */
	public void setNumTasks(Integer numTasks) {
		this.numTasks = numTasks;
	}

	/**
	 * @return the topologyMaxSpoutPending
	 */
	public Integer getTopologyMaxSpoutPending() {
		return topologyMaxSpoutPending;
	}

	/**
	 * @param topologyMaxSpoutPending the topologyMaxSpoutPending to set
	 */
	public void setTopologyMaxSpoutPending(Integer topologyMaxSpoutPending) {
		this.topologyMaxSpoutPending = topologyMaxSpoutPending;
	}

	/**
	 * @return the topologyMessageTimeoutSecs
	 */
	public Integer getTopologyMessageTimeoutSecs() {
		return topologyMessageTimeoutSecs;
	}

	/**
	 * @param topologyMessageTimeoutSecs the topologyMessageTimeoutSecs to set
	 */
	public void setTopologyMessageTimeoutSecs(Integer topologyMessageTimeoutSecs) {
		this.topologyMessageTimeoutSecs = topologyMessageTimeoutSecs;
	}

	/**
     * How many processes should be spawned around the cluster to execute this
     * topology. Each process will execute some number of tasks as threads within
     * them. This parameter should be used in conjunction with the parallelism hints
     * on each component in the topology to tune the performance of a topology.
     * {@link org.apache.storm.Config#setNumWorkers(Map, int)}
     */
    private Integer topologyWorkers = 2;

    /**
     * {@link org.apache.storm.topology.ComponentConfigurationDeclarer#setNumTasks(Number)}
     */
    private Integer numTasks = 1;


    /**
     * The maximum number of tuples that can be pending on a spout task at any given time.
     * This config applies to individual tasks, not to spouts or topologies as a whole.
     *
     * A pending tuple is one that has been emitted from a spout but has not been acked or failed yet.
     * Note that this config parameter has no effect for unreliable spouts that don't tag
     * their tuples with a message id.
     * {@link org.apache.storm.Config#setMaxSpoutPending(Map, int)}}
     */
    private Integer topologyMaxSpoutPending = 5000;

    /**
     * The maximum amount of time given to the topology to fully process a message
     * emitted by a spout. If the message is not acked within this time frame, Storm
     * will fail the message on the spout. Some spouts implementations will then replay
     * the message at a later time.
     * {@link org.apache.storm.Config#setMessageTimeoutSecs(Map, int)}
     */
    private Integer topologyMessageTimeoutSecs = 30;


}
