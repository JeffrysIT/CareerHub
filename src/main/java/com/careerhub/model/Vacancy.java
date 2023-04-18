package com.careerhub.model;


import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "vacancies")
public class Vacancy extends TimestampedEntity {
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

    @ManyToMany(mappedBy = "appliedVacancies")
    private List<Application> applications;

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
                ", applications=" + applications +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacancy vacancy1 = (Vacancy) o;
        return getId() == vacancy1.getId() && getViewed() == vacancy1.getViewed() && getApplied() == vacancy1.getApplied() && getTitle().equals(vacancy1.getTitle()) && Objects.equals(getDescription(), vacancy1.getDescription()) && Objects.equals(getSalary(), vacancy1.getSalary()) && Objects.equals(getLocation(), vacancy1.getLocation()) && Objects.equals(getApplications(), vacancy1.getApplications());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getDescription(), getSalary(), getLocation(), getViewed(), getApplied(), getApplications());
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

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }
}
