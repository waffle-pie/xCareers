package org.example.recruitment;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
	@JsonProperty
	private String href;

	@Builder
	private RecruitmentNotice(String site, String title, String company, String job, String career, String regular,
		String location, String deadline, String href) {
		this.site = site;
		this.title = title;
		this.company = company;
		this.job = job;
		this.career = career;
		this.regular = regular;
		this.location = location;
		this.deadline = deadline;
		this.href = href;
	}

	public static RecruitmentNotice create(String site, String title, String company, String job, String career,
		String regular, String location, String deadline) {
		return RecruitmentNotice.builder()
			.site(site)
			.title(title)
			.company(company)
			.job(job)
			.career(career)
			.regular(regular)
			.location(location)
			.deadline(deadline)
			.build();
	}

	public static RecruitmentNotice create(String site, String title, String href) {
		return RecruitmentNotice.builder()
			.site(site)
			.title(title)
			.href(href)
			.build();
	}

	public static RecruitmentNotice create(String site, String title, String company, String job, String career,
		String regular, String location) {
		return RecruitmentNotice.builder()
			.site(site)
			.title(title)
			.company(company)
			.job(job)
			.career(career)
			.regular(regular)
			.location(location)
			.build();
	}

}
