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

    @Column(name = "description")
    private String description;

    @Column(name = "salary")
    private String salary;

    @Column(name = "location")
    private String location;

    @Column(name = "viewed")
    private int viewed;

    @Column(name = "applied")
    private int applied;

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
    public String toString() {
        return "Vacancy{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", salary='" + salary + '\'' +
                ", location='" + location + '\'' +
                ", viewed=" + viewed +
                ", applied=" + applied +
                ", created=" + created +
                ", deleted=" + deleted +
                ", updated=" + updated +
                ", applicants=" + applicants.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacancy vacancy = (Vacancy) o;
        return getId() == vacancy.getId() &&
                getViewed() == vacancy.getViewed() &&
                getApplied() == vacancy.getApplied() &&
                getTitle().equals(vacancy.getTitle()) &&
                Objects.equals(getDescription(), vacancy.getDescription()) &&
                Objects.equals(getSalary(), vacancy.getSalary()) &&
                Objects.equals(getLocation(), vacancy.getLocation()) &&
                getCreated().equals(vacancy.getCreated()) &&
                Objects.equals(getDeleted(), vacancy.getDeleted()) &&
                Objects.equals(getUpdated(), vacancy.getUpdated()) &&
                Objects.equals(getApplicants(), vacancy.getApplicants());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getId(), getTitle(), getDescription(), getSalary(), getLocation(),
                getViewed(), getApplied(), getCreated(), getDeleted(), getUpdated(),
                getApplicants()
        );
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getViewed() {
        return viewed;
    }

    public void setViewed(int viewed) {
        this.viewed = viewed;
    }

    public int getApplied() {
        return applied;
    }

    public void setApplied(int applied) {
        this.applied = applied;
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
