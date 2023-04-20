package com.careerhub.dto;


import com.careerhub.model.ApplicationStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplicationRequestDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("coverLetter")
    private String coverLetter;

    @JsonProperty("status")
    private ApplicationStatus status;

    @JsonProperty("resumeId")
    private Long resumeId;

    @JsonProperty("userDetailsId")
    private Long userDetailsId;

    @JsonProperty("vacancyId")
    private Long vacancyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public Long getResumeId() {
        return resumeId;
    }

    public void setResumeId(Long resumeId) {
        this.resumeId = resumeId;
    }

    public Long getUserDetailsId() {
        return userDetailsId;
    }

    public void setUserDetailsId(Long userDetailsId) {
        this.userDetailsId = userDetailsId;
    }

    public Long getVacancyId() {
        return vacancyId;
    }

    public void setVacancyId(Long vacancyId) {
        this.vacancyId = vacancyId;
    }
}

