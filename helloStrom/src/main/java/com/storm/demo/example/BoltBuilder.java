package com.storm.demo.example;

import org.apache.storm.topology.IComponent;

public abstract class BoltBuilder {
    /**
	 * @return the parallelismHint
	 */
	public Integer getParallelismHint() {
		return parallelismHint;
	}

	/**
	 * @param parallelismHint the parallelismHint to set
	 */
	public void setParallelismHint(Integer parallelismHint) {
		this.parallelismHint = parallelismHint;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	private Integer parallelismHint = 5;

    private String id;

    abstract public IComponent buildBolt();
}
