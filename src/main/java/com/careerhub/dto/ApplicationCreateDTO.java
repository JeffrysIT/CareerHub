package com.careerhub.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplicationCreateDTO {

    @JsonProperty("coverLetter")
    private String coverLetter;

    @JsonProperty("resumeId")
    private Long resumeId;

    @JsonProperty("userDetailsId")
    private Long userDetailsId;

    @JsonProperty("vacancyId")
    private Long vacancyId;

    public String getCoverLetter() {
        return coverLetter;
    }

    public Long getResumeId() {
        return resumeId;
    }

    public Long getUserDetailsId() {
        return userDetailsId;
    }

    public Long getVacancyId() {
        return vacancyId;
    }
}
