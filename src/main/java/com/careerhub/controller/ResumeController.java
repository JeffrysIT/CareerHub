package com.careerhub.controller;

import com.careerhub.dto.ResumeCreateDTO;
import com.careerhub.dto.ResumeDTO;
import com.careerhub.dto.ResumeUpdateDTO;
import com.careerhub.model.Resume;
import com.careerhub.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/resume")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @PostMapping("/upload")
    public ResponseEntity<Long> uploadResume(@RequestParam("file") MultipartFile file,
                                             @RequestParam("candidateId") Long candidateId) throws IOException {

        Long resumeIdResponse = resumeService.upload(file, candidateId);
        return ResponseEntity.ok(resumeIdResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResumeDTO> updateResume(@PathVariable("id") Long id,
                                                  @RequestBody ResumeUpdateDTO resumeUpdateDTO) {
        ResumeDTO resumeDTO = resumeService.update(id, resumeUpdateDTO);
        return ResponseEntity.ok(resumeDTO);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteResume(@PathVariable("id") Long id) {
        resumeService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/candidate/{candidateId}")
    ResponseEntity<List<ResumeDTO>> getResumeList(
            @PathVariable("candidateId") Long candidateId
    ) {
        List<ResumeDTO> resumeDTOList = resumeService.getResumeList(candidateId);
        return ResponseEntity.ok(resumeDTOList);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadResume(@PathVariable Long id) {
        Resume resume = resumeService.getResumeById(id);
        ByteArrayResource resource = new ByteArrayResource(resume.getFileData());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resume.getFileName() + "\"")
                .contentLength(resume.getFileData().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

}

