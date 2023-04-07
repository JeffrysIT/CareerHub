package com.careerhub.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "vacancies")
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "job_position", nullable = false)
    private String jobPosition;

    @Column(name = "description")
    private String description;

    @Column(name = "viewed")
    private int viewed;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted")
    private LocalDateTime deleted;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated")
    private LocalDateTime updated;

    @ManyToMany(mappedBy = "appliedVacancies")
    private List<UserDetails> applicants;

    public Vacancy() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacancy vacancy = (Vacancy) o;
        return id == vacancy.id && viewed == vacancy.viewed && title.equals(vacancy.title) && Objects.equals(jobPosition, vacancy.jobPosition) && Objects.equals(description, vacancy.description) && created.equals(vacancy.created) && Objects.equals(deleted, vacancy.deleted) && Objects.equals(updated, vacancy.updated) && Objects.equals(applicants, vacancy.applicants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, jobPosition, description, viewed, created, deleted, updated, applicants);
    }

    @Override
    public String toString() {
        return "Vacancy{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", jobPosition='" + jobPosition + '\'' +
                ", description='" + description + '\'' +
                ", viewed=" + viewed +
                ", created=" + created +
                ", deleted=" + deleted +
                ", updated=" + updated +
                ", applicants=" + applicants +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getViewed() {
        return viewed;
    }

    public void setViewed(int viewed) {
        this.viewed = viewed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getDeleted() {
        return deleted;
    }

    public void setDeleted(LocalDateTime deleted) {
        this.deleted = deleted;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public List<UserDetails> getApplicants() {
        return applicants;
    }

    public void setApplicants(List<UserDetails> applicants) {
        this.applicants = applicants;
    }
}
