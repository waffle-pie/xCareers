package org.example.site.dynamic;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class DynamicSiteSetting {
	@JsonProperty
	private String siteName;
	@JsonProperty
	private String url;
	@JsonProperty
	private String jobListSelector;

}
