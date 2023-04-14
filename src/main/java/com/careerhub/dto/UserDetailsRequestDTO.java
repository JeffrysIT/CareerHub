package com.careerhub.dto;

import com.careerhub.model.Vacancy;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserDetailsRequestDTO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("resumeHtml")
    private String resumeHtml;

    @JsonProperty("appliedVacancies")
    private List<Vacancy> appliedVacancies;

    public UserDetailsRequestDTO() {
    }

    public UserDetailsRequestDTO(String name, String email, String resumeHtml, List<Vacancy> appliedVacancies) {
        this.name = name;
        this.email = email;
        this.resumeHtml = resumeHtml;
        this.appliedVacancies = appliedVacancies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResumeHtml() {
        return resumeHtml;
    }

    public void setResumeHtml(String resumeHtml) {
        this.resumeHtml = resumeHtml;
    }

    public List<Vacancy> getAppliedVacancies() {
        return appliedVacancies;
    }

    public void setAppliedVacancies(List<Vacancy> appliedVacancies) {
        this.appliedVacancies = appliedVacancies;
    }
}
