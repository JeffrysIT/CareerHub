package com.careerhub.service.impl;

import com.careerhub.dto.ResumeDTO;
import com.careerhub.dto.ResumeUpdateDTO;
import com.careerhub.dto.mapper.MapStructMapper;
import com.careerhub.exception.ResourceNotFoundException;
import com.careerhub.model.Resume;
import com.careerhub.model.Candidate;
import com.careerhub.repository.ResumeRepository;
import com.careerhub.repository.CandidateRepository;
import com.careerhub.service.CandidateService;
import com.careerhub.service.ResumeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    @Transactional
    public Long upload(MultipartFile file, Long candidateId) {
        Candidate candidate = candidateService.findCandidate(candidateId);

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
    public ResumeDTO getResumeById(Long id) {
        Resume resume = findResume(id);
        return mapper.mapToResumeDTO(resume);
    }

    @Override
    public ResumeDTO update(Long id, ResumeUpdateDTO resumeUpdateDTO) {
        Resume existingResume = findResume(id);

        existingResume.setFileName(resumeUpdateDTO.getFileName());
        existingResume.setFileData(resumeUpdateDTO.getFileData());

        Resume savedResume = resumeRepository.save(existingResume);
        return mapper.mapToResumeDTO(savedResume);
    }

    @Override
    public void delete(Long id) {
        Resume existingResume = findResume(id);
        existingResume.setDeleted(LocalDateTime.now());
        resumeRepository.save(existingResume);
    }

    @Override
    public List<ResumeDTO> getResumeList(Long candidateId) {
        Candidate candidate = candidateService.findCandidate(candidateId);
        return resumeRepository.findAllByCandidate(candidateId)
                .stream().map(mapper::mapToResumeDTO)
                .collect(Collectors.toList());
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
