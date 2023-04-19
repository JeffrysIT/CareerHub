package com.careerhub.service;

import com.careerhub.model.Resume;
import org.springframework.web.multipart.MultipartFile;

public interface ResumeService {
    void upload(MultipartFile file, Long userDetailsId);

    Resume getResumeById(long id);
}
