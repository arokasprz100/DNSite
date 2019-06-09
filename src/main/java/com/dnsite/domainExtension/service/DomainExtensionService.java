package com.dnsite.domainExtension.service;

import com.dnsite.domainExtension.model.DomainExtension;

import java.util.List;

public interface DomainExtensionService {
    void deleteInBatch(List<DomainExtension> domainExtensions);
    void saveInBatch(List<DomainExtension> domainExtensions);
    List<DomainExtension> findAll();
    DomainExtension findById(Long id);
}
