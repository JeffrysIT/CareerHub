package com.careerhub.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VacancyUpdateDTO {
    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("salary")
    private String salary;

    @JsonProperty("location")
    private String location;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getSalary() {
        return salary;
    }

    public String getLocation() {
        return location;
    }
}