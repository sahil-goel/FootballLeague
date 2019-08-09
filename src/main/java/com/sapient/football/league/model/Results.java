package com.sapient.football.league.model;

import java.io.Serializable;
import java.util.Map;

import org.springframework.hateoas.ResourceSupport;

import com.sapient.football.league.util.LinkBuilder;

@SuppressWarnings("serial")
public class Results<K> extends ResourceSupport implements Serializable {
	private final Map<String, K> results;

	@SafeVarargs
	public Results(Map<String, K> results, LinkBuilder<K>... linkBuilders) {
		super();
		this.results = results;
		if (linkBuilders != null && linkBuilders.length > 0) {
			for (LinkBuilder<K> linkBuilder : linkBuilders) {
				add(linkBuilder.transform(results));
			}
		}
	}

	public Map<String, K> getResults() {
		return results;
	}
}
