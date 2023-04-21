package com.careerhub.service.impl;

import com.careerhub.dto.ResumeCreateDTO;
import com.careerhub.dto.ResumeDTO;
import com.careerhub.exception.ResourceNotFoundException;
import com.careerhub.model.Resume;
import com.careerhub.model.UserDetails;
import com.careerhub.repository.ResumeRepository;
import com.careerhub.repository.UserDetailsRepository;
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
    private final UserDetailsRepository userDetailsrepository;

    public ResumeServiceImpl(ResumeRepository resumeRepository, UserDetailsRepository userDetailsrepository) {
        this.resumeRepository = resumeRepository;
        this.userDetailsrepository = userDetailsrepository;
    }

    @Override
    @Transactional
    public Long upload(MultipartFile file, Long userDetailsId) {
        UserDetails userDetails = userDetailsrepository.findByIdAndDeletedIsNull(userDetailsId);
        if (userDetails == null || userDetails.getDeleted() != null) {
            throw new ResourceNotFoundException("User Details not found by provided id: " + userDetailsId);
        }

        Resume resume = new Resume();
        resume.setFileName(file.getOriginalFilename());
        try {
            resume.setFileData(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error reading file data", e);
        }

        Resume resumeResponse = resumeRepository.save(resume);
        userDetails.getResumes().add(resumeResponse);
        userDetails.setUpdated(LocalDateTime.now());
        userDetailsrepository.save(userDetails);
        return userDetailsId;
    }

    @Override
    public Resume getResumeById(long id) {
        return null;
    }

    @Override
    public ResumeDTO update(Long id, ResumeCreateDTO resumeCreateDTO) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<ResumeDTO> getResumeList(Long userDetailsId) {
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
