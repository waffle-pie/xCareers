package org.example.site;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SiteSetting {
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



	public String getSiteName() {
		return siteName;
	}

	public String getUrl() {
		return url;
	}

	public String getJobListSelector() {
		return jobListSelector;
	}

	public String getTitleSelector() {
		return titleSelector;
	}

	public String getCompanySelector() {
		return companySelector;
	}

	public String getJobSelector() {
		return jobSelector;
	}

	public String getCareerSelector() {
		return careerSelector;
	}

	public String getRegularSelector() {
		return regularSelector;
	}

	public String getLocationSelector() {
		return locationSelector;
	}

	public String getDeadlineSelector() {
		return deadlineSelector;
	}
}
