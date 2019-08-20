package com.dnsite.domain.service;

import com.dnsite.domain.model.Domain;

import java.util.List;

public interface DomainService {

    void saveInBatch(List<Domain> domains);

    void deleteInBatch(List<Domain> domains);

    List<Domain> findAll();

    Domain findById(Long id);
}
