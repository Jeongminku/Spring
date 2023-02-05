package com.wss.constant;

public enum Role {
	ADMIN("관리자"), VIEWER("시청자"), STREAMER("방송인");
	
	
	
	
	private final String description;
	
	Role(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}