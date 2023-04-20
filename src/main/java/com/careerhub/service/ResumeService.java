package com.careerhub.service;

import com.careerhub.dto.ResumeRequestDTO;
import com.careerhub.dto.ResumeResponseDTO;
import com.careerhub.model.Resume;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResumeService {
    Long upload(MultipartFile file, Long userDetailsId);

    Resume getResumeById(long id);

    ResumeResponseDTO update(Long id, ResumeRequestDTO resumeRequestDTO);

    void delete(Long id);

    List<ResumeResponseDTO> getResumeList(Long userDetailsId);
}
