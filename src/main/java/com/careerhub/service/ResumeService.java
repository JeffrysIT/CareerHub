package com.careerhub.service;

import com.careerhub.dto.ResumeDTO;
import com.careerhub.dto.ResumeUpdateDTO;
import com.careerhub.model.Resume;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResumeService {
    Long upload(MultipartFile file, Long candidateId);

    ResumeDTO getResumeById(Long id);

    ResumeDTO update(Long id, ResumeUpdateDTO resumeUpdateDTO);

    void delete(Long id);

    List<ResumeDTO> getResumeList(Long candidateId);

    Resume findResume(Long resumeId);
}
