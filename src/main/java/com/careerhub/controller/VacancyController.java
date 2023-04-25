package com.careerhub.controller;

import com.careerhub.dto.VacancyCreateDTO;
import com.careerhub.dto.VacancyUpdateDTO;
import com.careerhub.dto.VacancyDTO;
import com.careerhub.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/vacancy")
public class VacancyController {

    @Autowired
    private VacancyService vacancyService;




}
