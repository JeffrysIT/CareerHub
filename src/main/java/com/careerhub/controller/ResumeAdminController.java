package com.careerhub.controller;

import com.careerhub.model.Resume;
import com.careerhub.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/admin/resume")
public class ResumeAdminController {

    @Autowired
    private ResumeService resumeService;


    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadResume(@PathVariable long id) {
        Resume resume = resumeService.getResumeById(id);
        ByteArrayResource resource = new ByteArrayResource(resume.getFileData());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resume.getFileName() + "\"")
                .contentLength(resume.getFileData().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

}
