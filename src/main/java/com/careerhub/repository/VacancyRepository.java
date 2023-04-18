package com.careerhub.repository;

import com.careerhub.model.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacancyRepository extends CrudRepository<Vacancy, Long> {
    Page<Vacancy> findAll(Pageable pageable);

    Page<Vacancy> searchByTitleContainingIgnoreCase(String query, PageRequest pageRequest);
}
