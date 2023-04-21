package com.careerhub.service;

import com.careerhub.dto.ResumeCreateDTO;
import com.careerhub.dto.ResumeDTO;
import com.careerhub.model.Resume;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResumeService {
    Long upload(MultipartFile file, Long userDetailsId);

    Resume getResumeById(long id);

    ResumeDTO update(Long id, ResumeCreateDTO resumeCreateDTO);

    void delete(Long id);

    List<ResumeDTO> getResumeList(Long userDetailsId);

    Resume findResume(Long resumeId);
}
