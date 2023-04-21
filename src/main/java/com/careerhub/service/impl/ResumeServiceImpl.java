package com.careerhub.service.impl;

import com.careerhub.dto.ResumeDTO;
import com.careerhub.dto.ResumeUpdateDTO;
import com.careerhub.exception.ResourceNotFoundException;
import com.careerhub.model.Resume;
import com.careerhub.model.Candidate;
import com.careerhub.repository.ResumeRepository;
import com.careerhub.repository.CandidateRepository;
import com.careerhub.service.ResumeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;
    private final CandidateRepository candidateRepository;

    public ResumeServiceImpl(ResumeRepository resumeRepository, CandidateRepository candidateRepository) {
        this.resumeRepository = resumeRepository;
        this.candidateRepository = candidateRepository;
    }

    @Override
    @Transactional
    public Long upload(MultipartFile file, Long candidateId) {
        Candidate candidate = candidateRepository.findByIdAndDeletedIsNull(candidateId);
        if (candidate == null || candidate.getDeleted() != null) {
            throw new ResourceNotFoundException("Candidate not found by provided id: " + candidateId);
        }

        Resume resume = new Resume();
        resume.setFileName(file.getOriginalFilename());
        try {
            resume.setFileData(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error reading file data", e);
        }

        Resume resumeResponse = resumeRepository.save(resume);
        candidate.getResumes().add(resumeResponse);
        candidate.setUpdated(LocalDateTime.now());
        candidateRepository.save(candidate);
        return candidateId;
    }

    @Override
    public Resume getResumeById(Long id) {
        return null;
    }

    @Override
    public ResumeDTO update(Long id, ResumeUpdateDTO resumeUpdateDTO) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<ResumeDTO> getResumeList(Long candidateId) {
        return null;
    }

    @Override
    public Resume findResume(Long resumeId) {
        if (resumeId == null) throw new IllegalArgumentException("resumeId can't be null or less than 0");
        Resume resume = resumeRepository.findByIdAndDeleteIsNull(resumeId);
        if (resume == null) {
            throw new ResourceNotFoundException("Resume not found by id: " + resumeId);
        }
        return resume;
    }
}
