package org.example.setting;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Deprecated
@Getter
public class StaticSiteSetting {
	@JsonProperty
	private String siteName;
	@JsonProperty
	private String url;
	@JsonProperty
	private String jobListSelector;
	@JsonProperty
	private String titleSelector;
	@JsonProperty
	private String companySelector;
	@JsonProperty
	private String jobSelector;
	@JsonProperty
	private String careerSelector;
	@JsonProperty
	private String regularSelector;
	@JsonProperty
	private String locationSelector;
	@JsonProperty
	private String deadlineSelector;

}
