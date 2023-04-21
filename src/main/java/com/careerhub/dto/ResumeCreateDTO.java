package com.careerhub.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResumeCreateDTO {

    @JsonProperty("file_name")
    private String fileName;

    @JsonProperty("file_data")
    private byte[] fileData;

    @JsonProperty("candidate_id")
    private Long candidateId;


    public String getFileName() {
        return fileName;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public Long getCandidateId() {
        return candidateId;
    }
}
