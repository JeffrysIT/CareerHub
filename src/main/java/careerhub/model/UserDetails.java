package careerhub.model;

import java.util.Objects;

public class UserDetails {
    private int id;
    private String name;
    private String email;
    private String resumeHtml;
    private long companyId;

    public UserDetails(int id, String name, String email, String password, String resumeHtml, int companyId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.resumeHtml = resumeHtml;
        this.companyId = companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDetails)) return false;
        UserDetails user = (UserDetails) o;
        return getId() == user.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getEmail(), getResumeHtml(), getCompanyId());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", resume='" + resumeHtml + '\'' +
                ", companyId=" + companyId +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }
}
