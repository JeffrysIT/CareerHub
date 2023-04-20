package com.careerhub.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class ResumeResponseDTO {
    @JsonProperty("id")
    private long id;

    @JsonProperty("file_name")
    private String fileName;

    @JsonProperty("user_details_id")
    private long userDetailsId;

    @JsonProperty("last_used")
    private LocalDateTime lastUsed;

}
