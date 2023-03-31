package careerhub.controller;

import careerhub.model.Vacancy;
import careerhub.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vacancies")
public class VacancyController {

    @Autowired
    private VacancyService vacancyService;

    @GetMapping
    public List<Vacancy> getAllVacancies() {
        return vacancyService.getAllVacancies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacancy> getVacancyById(@PathVariable("id") Long id) {
        Vacancy vacancy = vacancyService.getVacancyById(id);
        return ResponseEntity.ok(vacancy);
    }

    @PostMapping
    public ResponseEntity<Vacancy> createVacancy(@RequestBody Vacancy vacancy) {
        Vacancy createdVacancy = vacancyService.createVacancy(vacancy);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVacancy);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vacancy> updateVacancy(@PathVariable("id") Long id, @RequestBody Vacancy vacancy) {
        Vacancy updatedVacancy = vacancyService.updateVacancy(id, vacancy);
        return ResponseEntity.ok(updatedVacancy);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVacancy(@PathVariable("id") Long id) {
        vacancyService.deleteVacancy(id);
        return ResponseEntity.noContent().build();
    }
}
