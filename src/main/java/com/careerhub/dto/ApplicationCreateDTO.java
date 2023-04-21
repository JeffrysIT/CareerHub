package com.careerhub.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplicationCreateDTO {

    @JsonProperty("cover_letter")
    private String coverLetter;

    @JsonProperty("resume_id")
    private Long resumeId;

    @JsonProperty("candidate_id")
    private Long candidateId;

    @JsonProperty("vacancy_id")
    private Long vacancyId;

    public String getCoverLetter() {
        return coverLetter;
    }

    public Long getResumeId() {
        return resumeId;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public Long getVacancyId() {
        return vacancyId;
    }
}
