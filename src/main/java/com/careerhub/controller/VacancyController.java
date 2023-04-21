package com.careerhub.controller;

import com.careerhub.dto.VacancyCreateDTO;
import com.careerhub.dto.VacancyUpdateDTO;
import com.careerhub.dto.VacancyDTO;
import com.careerhub.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/vacancies")
public class VacancyController {
    @Autowired
    private VacancyService vacancyService;

    @GetMapping("/{id}")
    public ResponseEntity<VacancyDTO> getVacancyById(@PathVariable("id") Long id) {
        VacancyDTO vacancyDTO = vacancyService.getVacancyById(id);
        return ResponseEntity.ok(vacancyDTO);
    }

    @PutMapping("/{vacancyId}/apply/{applicationId}")
    public ResponseEntity<VacancyDTO> applyApplicationToVacancy(
            @PathVariable("vacancyId") Long vacancyId,
            @PathVariable("applicationId") Long applicationId
    ) {
        VacancyDTO vacancy = vacancyService.addApplicationToVacancy(vacancyId, applicationId);
        return ResponseEntity.ok(vacancy);
    }

    @GetMapping
    public ResponseEntity<Page<VacancyDTO>> getVacancies(
            @RequestParam(name = "sort", defaultValue = "created") String sort,
            @RequestParam(name = "order", defaultValue = "DESC") String order,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Page<VacancyDTO> vacancyPage = vacancyService.getVacancies(sort, order, page, size);
        return ResponseEntity.ok(vacancyPage);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<VacancyDTO>> searchVacancies(
            @RequestParam(name = "query") String query,
            @RequestParam(name = "sort", defaultValue = "created") String sort,
            @RequestParam(name = "order", defaultValue = "DESC") String order,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Page<VacancyDTO> vacancyPage = vacancyService.searchVacancies(query, sort, order, page, size);
        return ResponseEntity.ok(vacancyPage);
    }

    @PostMapping
    public ResponseEntity<VacancyDTO> createVacancy(@RequestBody VacancyCreateDTO vacancyCreateDTO) {
        VacancyDTO vacancyDTO = vacancyService.createVacancy(vacancyCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(vacancyDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VacancyDTO> updateVacancy(
            @PathVariable("id") Long id,
            @RequestBody VacancyUpdateDTO vacancyUpdateDTO
    ) {
        VacancyDTO vacancyDTO = vacancyService.updateVacancy(id, vacancyUpdateDTO);
        return ResponseEntity.ok(vacancyDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVacancy(@PathVariable("id") Long id) {
        vacancyService.deleteVacancy(id);
        return ResponseEntity.noContent().build();
    }

}
