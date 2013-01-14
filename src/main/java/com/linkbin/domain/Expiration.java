package com.linkbin.domain;

/**
 * Expriation in minutes
 * 
 * @author shekhargulati
 * 
 */
public enum Expiration {
	NEVER(-1L), TEN_MINUTES(10L), ONE_MINUTE(1L), ONE_HOUR(60L);

	private final long duration;

	Expiration(long duration) {
		this.duration = duration;
	}

	public long getDuration() {
		return duration;
	}
}