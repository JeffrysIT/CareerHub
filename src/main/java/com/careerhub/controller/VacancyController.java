package com.careerhub.controller;

import com.careerhub.dto.VacancyResponseDTO;
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
    public ResponseEntity<VacancyResponseDTO> getVacancyById(@PathVariable("id") Long id) {
        VacancyResponseDTO vacancy = vacancyService.getVacancyById(id);
        return ResponseEntity.ok(vacancy);
    }

    @PutMapping("/{vacancyId}/apply/{userId}")
    public ResponseEntity<VacancyResponseDTO> applyUserToVacancy(
            @PathVariable("vacancyId") Long vacancyId,
            @PathVariable("userId") Long userId
    ) {
        VacancyResponseDTO vacancy = vacancyService.addUserToVacancy(vacancyId, userId);
        return ResponseEntity.ok(vacancy);
    }

    @GetMapping
    public ResponseEntity<Page<VacancyResponseDTO>> getVacancies(
            @RequestParam(name = "sort", defaultValue = "created") String sort,
            @RequestParam(name = "order", defaultValue = "DESC") String order,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Page<VacancyResponseDTO> vacancyPage = vacancyService.getVacancies(sort, order, page, size);
        return ResponseEntity.ok(vacancyPage);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<VacancyResponseDTO>> searchVacancies(
            @RequestParam(name = "query") String query,
            @RequestParam(name = "sort", defaultValue = "created") String sort,
            @RequestParam(name = "order", defaultValue = "DESC") String order,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Page<VacancyResponseDTO> vacancyPage = vacancyService.searchVacancies(query, sort, order, page, size);
        return ResponseEntity.ok(vacancyPage);
    }
}
