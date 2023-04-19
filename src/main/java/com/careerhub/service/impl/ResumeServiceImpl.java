package com.careerhub.service.impl;

import com.careerhub.model.Resume;
import com.careerhub.repository.ResumeRepository;
import com.careerhub.service.ResumeService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;

    public ResumeServiceImpl(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    @Override
    public void upload(MultipartFile file, Long userDetailsId) {

    }

    @Override
    public Resume getResumeById(long id) {
        return null;
    }
}
