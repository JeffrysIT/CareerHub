package com.careerhub.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "applications")
public class Application extends TimestampedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cover_letter")
    private String coverLetter;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ApplicationStatus status;

    @ManyToOne
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @ManyToOne
    @JoinColumn(name = "user_details_id", nullable = false)
    private UserDetails userDetails;

    @ManyToOne
    @JoinColumn(name = "vacancy_id")
    private Vacancy vacancy;

    public Application() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return getId().equals(that.getId()) && Objects.equals(getCoverLetter(), that.getCoverLetter()) && getStatus() == that.getStatus() && Objects.equals(getResume(), that.getResume()) && Objects.equals(getUserDetails(), that.getUserDetails()) && Objects.equals(getVacancy(), that.getVacancy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCoverLetter(), getStatus(), getResume(), getUserDetails(), getVacancy());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }
}
