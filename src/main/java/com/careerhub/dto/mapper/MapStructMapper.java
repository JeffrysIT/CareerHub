package com.careerhub.dto.mapper;

import com.careerhub.dto.*;
import com.careerhub.model.Application;
import com.careerhub.model.Resume;
import com.careerhub.model.Candidate;
import com.careerhub.model.Vacancy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    @Mapping(target = "vacancy", source = "vacancyId")
    @Mapping(target = "resume", source = "resumeId")
    @Mapping(target = "candidate", source = "candidateId")
    Application mapToApplicationEntity(ApplicationCreateDTO applicationCreateDTO);

    Candidate mapToCandidateEntity(CandidateCreateDTO candidateCreateDTO);

    Vacancy mapToVacancyEntity(VacancyCreateDTO vacancyCreateDTO);

    @Mapping(source = "resume.id", target = "resumeId")
    @Mapping(source = "candidate.id", target = "candidateId")
    @Mapping(source = "vacancy.id", target = "vacancyId")
    ApplicationDTO mapToApplicationDTO(Application savedApplication);

    CandidateDTO mapToCandidateDTO(Candidate candidate);

    VacancyDTO mapToVacancyDTO(Vacancy vacancy);

    ResumeDTO mapToResumeDTO(Resume resume);

    Resume mapToResumeEntity(ResumeCreateDTO resumeCreateDTO);

    Resume mapToResumeEntity(ResumeDTO resumeDTO);

    default Long mapVacancyToLong(Vacancy vacancy) {
        return vacancy != null ? vacancy.getId() : null;
    }

    default Long mapResumeToLong(Resume resume) {
        return resume != null ? resume.getId() : null;
    }

    default Long mapCandidateToLong(Candidate candidate) {
        return candidate != null ? candidate.getId() : null;
    }

    default Vacancy mapVacancyIdToVacancy(Long vacancyId) {
        Vacancy vacancy = new Vacancy();
        vacancy.setId(vacancyId);
        return vacancy;
    }

    default Resume mapResumeIdToResume(Long resumeId) {
        Resume resume = new Resume();
        resume.setId(resumeId);
        return resume;
    }

    default Candidate mapCandidateIdToCandidate(Long candidateId) {
        Candidate candidate = new Candidate();
        candidate.setId(candidateId);
        return candidate;
    }

}
