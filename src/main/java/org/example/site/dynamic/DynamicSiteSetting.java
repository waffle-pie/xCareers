package org.example.site.dynamic;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

public class DynamicSiteSetting {
	@JsonProperty
	private String siteName;
	@JsonProperty
	private String url;
	@JsonProperty
	private String jobListSelector;

	public String getSiteName() {
		return siteName;
	}

	public String getUrl() {
		return url;
	}

	public String getJobListSelector() {
		return jobListSelector;
	}
}
