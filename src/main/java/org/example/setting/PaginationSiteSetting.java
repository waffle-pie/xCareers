package org.example.setting;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class PaginationSiteSetting {
	@JsonProperty
	private String siteName;
	@JsonProperty
	private String url;
	@JsonProperty
	private String jobListSelector;
	@JsonProperty
	private String jobDetailSelector;
	@JsonProperty
	private String paginationNextButtonSelector;

}
