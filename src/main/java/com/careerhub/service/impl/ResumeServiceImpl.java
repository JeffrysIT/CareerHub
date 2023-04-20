package com.careerhub.service.impl;

import com.careerhub.dto.ResumeRequestDTO;
import com.careerhub.dto.ResumeResponseDTO;
import com.careerhub.model.Resume;
import com.careerhub.repository.ResumeRepository;
import com.careerhub.service.ResumeService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;

    public ResumeServiceImpl(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    @Override
    public Long upload(MultipartFile file, Long userDetailsId) {

        return userDetailsId;
    }

    @Override
    public Resume getResumeById(long id) {
        return null;
    }

    @Override
    public ResumeResponseDTO update(Long id, ResumeRequestDTO resumeRequestDTO) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<ResumeResponseDTO> getResumeList(Long userDetailsId) {
        return null;
    }
}
