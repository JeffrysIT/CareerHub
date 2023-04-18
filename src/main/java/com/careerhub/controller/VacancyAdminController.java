package com.careerhub.controller;

import com.careerhub.dto.VacancyRequestDTO;
import com.careerhub.dto.VacancyResponseDTO;
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
    public ResponseEntity<VacancyResponseDTO> createVacancy(@RequestBody VacancyRequestDTO vacancyRequestDTO) {
        VacancyResponseDTO vacancy = vacancyService.createVacancy(vacancyRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(vacancy);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VacancyResponseDTO> updateVacancy(
            @PathVariable("id") Long id,
            @RequestBody VacancyRequestDTO vacancyRequestDTO
    ) {
        VacancyResponseDTO vacancy = vacancyService.updateVacancy(id, vacancyRequestDTO);
        return ResponseEntity.ok(vacancy);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<VacancyResponseDTO> deleteVacancy(@PathVariable("id") Long id) {
        vacancyService.deleteVacancy(id);
        return ResponseEntity.noContent().build();
    }

}
