package org.example.recruitment;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitmentNotice {
	@JsonProperty
	private String site;
	@JsonProperty
	private String title;
	@JsonProperty
	private String company;
	@JsonProperty
	private String job;
	@JsonProperty
	private String career;
	@JsonProperty
	private String regular;
	@JsonProperty
	private String location;

	@JsonProperty
	private String deadline;

	@Builder
	private RecruitmentNotice(String site, String title, String company, String job, String career, String regular,
		String location, String deadline) {
		this.site = site;
		this.title = title;
		this.company = company;
		this.job = job;
		this.career = career;
		this.regular = regular;
		this.location = location;
		this.deadline = deadline;
	}

	@Builder
	private RecruitmentNotice(String site, String title, String company, String job, String career, String regular,
		String location) {
		this.site = site;
		this.title = title;
		this.company = company;
		this.job = job;
		this.career = career;
		this.regular = regular;
		this.location = location;
	}

	public static RecruitmentNotice create(String site, String title, String company, String job, String career,
		String regular, String location) {
		return new RecruitmentNotice(site, title, company, job, career, regular, location);
	}

	public static RecruitmentNotice create(String site, String title, String company, String job, String career,
		String regular, String location, String deadline) {
		return new RecruitmentNotice(site, title, company, job, career, regular, location, deadline);
	}
}
