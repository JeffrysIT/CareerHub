package careerhub.model;

import java.util.List;
import java.util.Objects;

public class Company {
    private int id;
    private String name;
    private String description;
    private String contactInfo;
    private List<UserDetails> employees;
    private List<Vacancy> vacancies;

    public Company(int id, String name, String description, String contactInfo, List<UserDetails> employees, List<Vacancy> vacancies) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.contactInfo = contactInfo;
        this.employees = employees;
        this.vacancies = vacancies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;
        Company company = (Company) o;
        return getId() == company.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getContactInfo(), getEmployees(), getVacancies());
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                ", employees=" + employees +
                ", vacancies=" + vacancies +
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public List<UserDetails> getEmployees() {
        return employees;
    }

    public void setEmployees(List<UserDetails> employees) {
        this.employees = employees;
    }

    public List<Vacancy> getVacancies() {
        return vacancies;
    }

    public void setVacancies(List<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }

}
