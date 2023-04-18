package com.careerhub.model;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.time.LocalDateTime;

public class TimestampedEntity {
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted")
    private LocalDateTime deleted;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated")
    private LocalDateTime updated;

    @Override
    public String toString() {
        return "TimestampedEntity{" +
                "created=" + created +
                ", deleted=" + deleted +
                ", updated=" + updated +
                '}';
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
}
