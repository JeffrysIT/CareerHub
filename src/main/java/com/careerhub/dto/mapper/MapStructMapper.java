package com.careerhub.dto.mapper;

import com.careerhub.dto.ApplicationCreateDTO;
import com.careerhub.dto.ApplicationDTO;
import com.careerhub.model.Application;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    Application applicationCreateDTOtoApplication(ApplicationCreateDTO applicationCreateDTO);

    ApplicationDTO applicationToApplicationDTO(Application savedApplication);
}
