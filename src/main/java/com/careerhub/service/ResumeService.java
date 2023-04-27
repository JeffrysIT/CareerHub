package com.careerhub.service;

import com.careerhub.dto.ResumeDTO;
import com.careerhub.model.Resume;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResumeService {
    ResumeDTO upload(MultipartFile file, Long candidateId);

    Resume findResume(Long resumeId);

    List<ResumeDTO> getResumeList(Long candidateId, String sort, String order);

    Resource download(Long vacancyId, Long candidateId, Long applicationId, Long resumeId);

    Resource download(Long candidateId, Long resumeId);

    void delete(Long candidateId, Long resumeId);

}
