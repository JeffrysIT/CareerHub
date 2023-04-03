package careerhub.model;

import javax.persistence.*;
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

    @ManyToMany(mappedBy = "appliedVacancies")
    private List<UserDetails> applicants;

    public Vacancy() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vacancy)) return false;
        Vacancy vacancy = (Vacancy) o;
        return getId() == vacancy.getId() && getTitle().equals(vacancy.getTitle()) && Objects.equals(getDescription(), vacancy.getDescription()) && Objects.equals(getApplicants(), vacancy.getApplicants());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getDescription(), getApplicants());
    }

    @Override
    public String toString() {
        return "Vacancy{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
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

    public List<UserDetails> getApplicants() {
        return applicants;
    }

    public void setApplicants(List<UserDetails> applicants) {
        this.applicants = applicants;
    }
}
