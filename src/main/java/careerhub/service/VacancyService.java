package careerhub.service;

import careerhub.model.Vacancy;

import java.util.Collection;

public interface VacancyService {
    Vacancy createVacancy(Vacancy vacancy);

    Vacancy updateVacancy(Vacancy vacancy);

    void deleteVacancy(Long id);

    Vacancy getVacancyById(Long id);

    Collection<Vacancy> getAllVacancies();
}
