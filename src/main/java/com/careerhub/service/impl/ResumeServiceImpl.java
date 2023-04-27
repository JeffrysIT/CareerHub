package com.careerhub.service.impl;

import com.careerhub.dto.ResumeDTO;
import com.careerhub.dto.mapper.MapStructMapper;
import com.careerhub.exception.ResourceNotFoundException;
import com.careerhub.model.Resume;
import com.careerhub.model.Candidate;
import com.careerhub.repository.ResumeRepository;
import com.careerhub.repository.CandidateRepository;
import com.careerhub.service.CandidateService;
import com.careerhub.service.ResumeService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO: lastused impl
 */
@Service
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;
    private final CandidateService candidateService;
    private final CandidateRepository candidateRepository;

    private final MapStructMapper mapper;

    public ResumeServiceImpl(ResumeRepository resumeRepository,
                             CandidateService candidateService,
                             CandidateRepository candidateRepository, MapStructMapper mapper) {
        this.resumeRepository = resumeRepository;
        this.candidateService = candidateService;
        this.candidateRepository = candidateRepository;
        this.mapper = mapper;
    }

    /**
     * TODO: validate file and handle exception
     */
    @Override
    @Transactional
    public ResumeDTO upload(MultipartFile file, Long candidateId) {
        Candidate candidate = candidateService.findCandidate(candidateId);

        Resume resume = new Resume();
        resume.setFileName(file.getOriginalFilename());
        try {
            resume.setFileData(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error reading file data", e);
        }
        resume.setCreated(LocalDateTime.now());
        Resume resumeResponse = resumeRepository.save(resume);

        candidate.getResumes().add(resumeResponse);
        candidate.setUpdated(LocalDateTime.now());
        candidateRepository.save(candidate);

        return mapper.mapToResumeDTO(resumeResponse);
    }

    @Override
    public void delete(Long candidateId, Long resumeID) {
        Resume existingResume = findResume(resumeID);
        existingResume.setDeleted(LocalDateTime.now());
        resumeRepository.save(existingResume);
    }

    @Override
    public List<ResumeDTO> getResumeList(Long candidateId, String sort, String order) {
        Candidate candidate = candidateService.findCandidate(candidateId);
        return resumeRepository.findAllByCandidate(candidate)
                .stream().map(mapper::mapToResumeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Resume findResume(Long resumeId) {
        if (resumeId == null || resumeId < 0) throw new IllegalArgumentException("Invalid parameter resumeId");
        Resume resume = resumeRepository.findByIdAndDeletedIsNull(resumeId);
        if (resume == null) throw new ResourceNotFoundException("Resume not found");
        return resume;
    }

    private Resume findResume(Long vacancyId, Long candidateId, Long applicationId, Long resumeId) {
        if (vacancyId == null || vacancyId < 0)
            throw new IllegalArgumentException("Invalid parameter vacancyId");

        if (candidateId == null || candidateId < 0)
            throw new IllegalArgumentException("Invalid parameter candidateId");

        if (applicationId == null || applicationId < 0)
            throw new IllegalArgumentException("Invalid parameter applicationId");

        if (resumeId == null || resumeId < 0)
            throw new IllegalArgumentException("Invalid parameter resumeId");

        Resume resume = resumeRepository
                .findByCandidateIdAndIdAndApplicationsVacancyIdAndApplicationsId(
                        candidateId, resumeId, vacancyId, applicationId
                );
        if (resume == null) throw new ResourceNotFoundException("Resume not found");

        return resume;
    }

    @Override
    public Resource download(Long vacancyId, Long candidateId, Long applicationId, Long resumeId) {
        Resume resume = findResume(vacancyId, candidateId, applicationId, resumeId);
        return new ByteArrayResource(resume.getFileData());
    }

    @Override
    public Resource download(Long candidateId, Long resumeId) {
        Resume resume = findResume(candidateId, resumeId);
        return new ByteArrayResource(resume.getFileData());
    }

    private Resume findResume(Long candidateId, Long resumeId) {
        if (candidateId == null || candidateId < 0)
            throw new IllegalArgumentException("Invalid parameter candidateId");

        if (resumeId == null || resumeId < 0)
            throw new IllegalArgumentException("Invalid parameter resumeId");

        Resume resume = resumeRepository.findByCandidateIdAndId(candidateId, resumeId);
        if (resume == null) throw new ResourceNotFoundException("Resume not found");
        return resume;
    }
}
