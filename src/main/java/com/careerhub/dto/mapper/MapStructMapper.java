package com.careerhub.dto.mapper;

import com.careerhub.dto.*;
import com.careerhub.model.Application;
import com.careerhub.model.Resume;
import com.careerhub.model.UserDetails;
import com.careerhub.model.Vacancy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    @Mapping(target = "vacancy", source = "vacancyId")
    @Mapping(target = "resume", source = "resumeId")
    @Mapping(target = "userDetails", source = "userDetailsId")
    Application mapToApplicationEntity(ApplicationCreateDTO applicationCreateDTO);

    UserDetails mapToUserDetailsEntity(UserDetailsCreateDTO userDetailsCreateDTO);

    Vacancy mapToVacancyEntity(VacancyCreateDTO vacancyCreateDTO);

    @Mapping(source = "resume.id", target = "resumeId")
    @Mapping(source = "userDetails.id", target = "userDetailsId")
    @Mapping(source = "vacancy.id", target = "vacancyId")
    ApplicationDTO mapToApplicationDTO(Application savedApplication);

    UserDetailsDTO mapToUserDetailsDTO(UserDetails userDetails);

    VacancyDTO mapToVacancyDTO(Vacancy vacancy);

    default Long mapVacancyToLong(Vacancy vacancy) {
        return vacancy != null ? vacancy.getId() : null;
    }

    default Long mapResumeToLong(Resume resume) {
        return resume != null ? resume.getId() : null;
    }

    default Long mapUserDetailsToLong(UserDetails userDetails) {
        return userDetails != null ? userDetails.getId() : null;
    }

}
