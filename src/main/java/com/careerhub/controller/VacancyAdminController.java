package com.careerhub.controller;

import com.careerhub.dto.VacancyRequestDTO;
import com.careerhub.model.Vacancy;
import com.careerhub.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/admin/vacancies")
public class VacancyAdminController {
    @Autowired
    private VacancyService vacancyService;

    @PostMapping
    public ResponseEntity<Vacancy> createVacancy(@RequestBody VacancyRequestDTO vacancyRequestDTO) {
        Vacancy vacancy = vacancyService.createVacancy(vacancyRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(vacancy);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vacancy> updateVacancy(@PathVariable("id") Long id, @RequestBody VacancyRequestDTO vacancyRequestDTO) {
        Vacancy vacancy = vacancyService.updateVacancy(id, vacancyRequestDTO);
        return ResponseEntity.ok(vacancy);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVacancy(@PathVariable("id") Long id) {
        vacancyService.deleteVacancy(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{vacancyId}/delete/{userId}")
    public ResponseEntity<Vacancy> deleteUserFromVacancy(@PathVariable("vacancyId") Long vacancyId, @PathVariable("userId") Long userId) {
        Vacancy vacancy = vacancyService.addUserToVacancy(vacancyId, userId);
        return ResponseEntity.ok(vacancy);
    }

}
