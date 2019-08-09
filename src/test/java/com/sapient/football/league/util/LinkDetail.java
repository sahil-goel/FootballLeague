package com.sapient.football.league.util;

public class LinkDetail {

	private final String rel;
	private final String href;

	public LinkDetail(String rel, String href) {
		this.rel = rel;
		this.href = href;
	}

	public String getRel() {
		return rel;
	}

	public String getHref() {
		return href;
	}
}
