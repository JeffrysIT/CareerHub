package careerhub.service.impl;

import careerhub.model.Vacancy;
import careerhub.repository.VacancyRepository;
import careerhub.service.VacancyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VacancyServiceImpl implements VacancyService {

    private final VacancyRepository vacancyRepository;

    public VacancyServiceImpl(VacancyRepository vacancyRepository) {
        this.vacancyRepository = vacancyRepository;
    }

    @Override
    public Vacancy createVacancy(Vacancy vacancy) {
        return vacancyRepository.save(vacancy);
    }

    @Override
    public Vacancy updateVacancy(Long id, Vacancy vacancy) {
        return vacancyRepository.save(vacancy);
    }

    @Override
    public void deleteVacancy(Long id) {
        vacancyRepository.deleteById(id);
    }

    @Override
    public Vacancy getVacancyById(Long id) {
        return vacancyRepository.findById(id).orElseThrow(() -> new RuntimeException("Vacancy not found"));
    }

    @Override
    public List<Vacancy> getAllVacancies() {
        return vacancyRepository.findAll();
    }
}
