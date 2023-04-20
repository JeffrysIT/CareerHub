package com.careerhub.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplicationUpdateDTO {

    @JsonProperty("coverLetter")
    private String coverLetter;

    @JsonProperty("resumeId")
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

