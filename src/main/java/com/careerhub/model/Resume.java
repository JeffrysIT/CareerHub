package com.careerhub.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "resumes", indexes = {@Index(name = "idx_resume_last_used", columnList = "last_used")})
public class Resume extends TimestampedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_data", nullable = false)
    private byte[] fileData;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_details_id", nullable = false)
    private UserDetails userDetails;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_used")
    private LocalDateTime lastUsed;

    @OneToMany(mappedBy = "resume")
    private List<Application> applications;

    public Resume() {
    }

    @Override
    public String toString() {
        return "Resume{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", userDetails=" + userDetails +
                ", lastUsed=" + lastUsed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return getId() == resume.getId() &&
                getFileName().equals(resume.getFileName()) &&
                Arrays.equals(getFileData(), resume.getFileData()) &&
                getUserDetails().equals(resume.getUserDetails()) &&
                Objects.equals(getLastUsed(), resume.getLastUsed()) &&
                Objects.equals(getApplications(), resume.getApplications());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getId(), getFileName(), getUserDetails(), getLastUsed(), getApplications());
        result = 31 * result + Arrays.hashCode(getFileData());
        return result;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public LocalDateTime getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(LocalDateTime lastUsed) {
        this.lastUsed = lastUsed;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }
}
