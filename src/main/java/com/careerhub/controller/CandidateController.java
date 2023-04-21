package com.careerhub.controller;

import com.careerhub.dto.CandidateCreateDTO;
import com.careerhub.dto.CandidateDTO;
import com.careerhub.dto.CandidateUpdateDTO;
import com.careerhub.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @GetMapping("/{id}")
    public ResponseEntity<CandidateDTO> getCandidate(@PathVariable("id") Long id) {
        CandidateDTO candidateDTO = candidateService.getCandidateById(id);
        return ResponseEntity.ok(candidateDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidateDTO> updateCandidate(@PathVariable("id") Long id,
                                                        @RequestBody CandidateUpdateDTO candidateUpdateDTO) {
        CandidateDTO updatedCandidate = candidateService.updateCandidate(id, candidateUpdateDTO);
        return ResponseEntity.ok(updatedCandidate);
    }

    @PostMapping
    public ResponseEntity<CandidateDTO> createCandidate(@RequestBody CandidateCreateDTO candidateCreateDTO) {
        CandidateDTO createdCandidate = candidateService.saveCandidate(candidateCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCandidate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable("id") Long id) {
        candidateService.deleteCandidate(id);
        return ResponseEntity.noContent().build();
    }

}
