package com.careerhub.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResumeCreateDTO {

    @JsonProperty("file_name")
    private String fileName;

    @JsonProperty("file_data")
    private byte[] fileData;

    @JsonProperty("user_details_id")
    private Long userDetailsId;


    public String getFileName() {
        return fileName;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public Long getUserDetailsId() {
        return userDetailsId;
    }
}
