package careerhub.service;

import careerhub.model.Vacancy;

import java.util.List;

public interface VacancyService {
    Vacancy createVacancy(Vacancy vacancy);

    Vacancy updateVacancy(Long id, Vacancy vacancy);

    void deleteVacancy(Long id);

    Vacancy getVacancyById(Long id);

    List<Vacancy> getAllVacancies();
}
