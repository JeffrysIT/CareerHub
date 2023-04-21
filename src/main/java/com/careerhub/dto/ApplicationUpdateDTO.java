package com.careerhub.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplicationUpdateDTO {

    @JsonProperty(value = "coverLetter", required = true)
    private String coverLetter;

    @JsonProperty(value = "resumeId", required = true)
    private Long resumeId;

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    public Long getResumeId() {
        return resumeId;
    }

    public void setResumeId(Long resumeId) {
        this.resumeId = resumeId;
    }
}

