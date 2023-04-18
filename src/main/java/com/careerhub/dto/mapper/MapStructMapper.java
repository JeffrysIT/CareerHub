package com.careerhub.dto.mapper;

import com.careerhub.dto.UserDetailsRequestDTO;
import com.careerhub.dto.VacancyRequestDTO;
import com.careerhub.dto.VacancyResponseDTO;
import com.careerhub.model.UserDetails;
import com.careerhub.model.Vacancy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    VacancyRequestDTO vacancyToVacancyRequestDTO(Vacancy vacancy);

    Vacancy vacancyRequestDTOtoVacancy(VacancyRequestDTO vacancyRequestDTO);

    VacancyResponseDTO vacancyToVacancyResponseDTO(Vacancy vacancy);

    Vacancy vacancyResponseDTOtoVacancy(VacancyResponseDTO vacancyResponseDTO);

    UserDetailsRequestDTO userDetailsToUserDetailsRequestDTO(UserDetails userDetails);

    UserDetails userDetailsRequestDTOtoUserDetails(UserDetailsRequestDTO userDetailsRequestDTO);

}
