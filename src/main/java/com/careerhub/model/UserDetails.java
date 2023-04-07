package com.careerhub.model;


import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user_details")
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "resume_html")
    private String resumeHtml;

    @ManyToMany
    @JoinTable(
            name = "applied_vacancies",
            joinColumns = {@JoinColumn(name = "user_details_id")},
            inverseJoinColumns = {@JoinColumn(name = "vacancy_id")}
    )
    private List<Vacancy> appliedVacancies;

    public UserDetails() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetails that = (UserDetails) o;
        return id == that.id && Objects.equals(name, that.name) && email.equals(that.email) && Objects.equals(resumeHtml, that.resumeHtml) && Objects.equals(appliedVacancies, that.appliedVacancies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, resumeHtml, appliedVacancies);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
