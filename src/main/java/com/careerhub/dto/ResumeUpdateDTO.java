package com.careerhub.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResumeUpdateDTO {
    @JsonProperty(value = "file_name", required = true)
    private String fileName;

    @JsonProperty(value = "file_data", required = true)
    private byte[] fileData;

    public String getFileName() {
        return fileName;
    }

    public byte[] getFileData() {
        return fileData;
    }
}
