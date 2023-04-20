package com.careerhub.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResumeRequestDTO {
    @JsonProperty("file_name")
    private String fileName;

    @JsonProperty("file_data")
    private byte[] fileData;

    @JsonProperty("user_details_id")
    private long userDetailsId;
}
