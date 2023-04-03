package careerhub.service.impl;

import careerhub.exception.ResourceNotFoundException;
import careerhub.model.Vacancy;
import careerhub.repository.VacancyRepository;
import careerhub.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class VacancyServiceImpl implements VacancyService {

    private final VacancyRepository vacancyRepository;

    @Autowired
    public VacancyServiceImpl(VacancyRepository vacancyRepository) {
        this.vacancyRepository = vacancyRepository;
    }

    @Override
    public Vacancy createVacancy(Vacancy vacancy) {
        return vacancyRepository.save(vacancy);
    }

    @Override
    public Vacancy updateVacancy(Vacancy vacancy) {
        Long vacancyId = vacancy.getId();
        Vacancy existingVacancy = vacancyRepository.findById(vacancyId)
                .orElseThrow(() -> new ResourceNotFoundException("Vacancy not found with id: " + vacancyId));
        existingVacancy.setTitle(vacancy.getTitle());
        existingVacancy.setDescription(vacancy.getDescription());
        existingVacancy.setViewed(vacancy.getViewed());
        existingVacancy.setApplicants(vacancy.getApplicants());

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
    public Collection<Vacancy> getAllVacancies() {
        return vacancyRepository.findAll();
    }
}
