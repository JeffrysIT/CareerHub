package com.careerhub.controller;

import com.careerhub.model.Vacancy;
import com.careerhub.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/vacancies")
public class VacancyController {
    @Autowired
    private VacancyService vacancyService;

    @GetMapping("/{id}")
    public ResponseEntity<Vacancy> getVacancyById(@PathVariable("id") Long id) {
        Vacancy vacancy = vacancyService.getVacancyById(id);
        return ResponseEntity.ok(vacancy);
    }

    @PutMapping("/{vacancyId}/apply/{userId}")
    public ResponseEntity<Vacancy> applyUserToVacancy(@PathVariable("vacancyId") Long vacancyId, @PathVariable("userId") Long userId) {
        Vacancy vacancy = vacancyService.addUserToVacancy(vacancyId, userId);
        return ResponseEntity.ok(vacancy);
    }

    @GetMapping
    public ResponseEntity<Page<Vacancy>> getVacancies(
            @RequestParam(name = "sort", defaultValue = "created") String sortBy,
            @RequestParam(name = "direction", defaultValue = "DESC") String direction,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Page<Vacancy> vacancyPage = vacancyService.getVacancies(sortBy, direction, page, size);
        return ResponseEntity.ok(vacancyPage);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Vacancy>> searchVacancies(
            @RequestParam(name = "query") String query,
            @RequestParam(name = "sort", defaultValue = "created") String sort,
            @RequestParam(name = "order", defaultValue = "DESC") String order,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Page<Vacancy> vacancyPage = vacancyService.searchVacancies(query, sort, order, page, size);
        return ResponseEntity.ok(vacancyPage);
    }
}
