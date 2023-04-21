package com.careerhub.service;

import com.careerhub.dto.ResumeDTO;
import com.careerhub.dto.ResumeUpdateDTO;
import com.careerhub.model.Resume;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResumeService {
    Long upload(MultipartFile file, Long candidateId);

    Resume getResumeById(Long id);

    ResumeDTO update(Long id, ResumeUpdateDTO resumeUpdateDTO);

    void delete(Long id);

    List<ResumeDTO> getResumeList(Long candidateId);

    Resume findResume(Long resumeId);
}
