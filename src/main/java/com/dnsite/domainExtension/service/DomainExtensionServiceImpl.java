package com.dnsite.domainExtension.service;

import com.dnsite.domainExtension.model.DomainExtension;
import com.dnsite.domainExtension.repository.DomainExtensionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DomainExtensionServiceImpl implements DomainExtensionService {

    @Autowired
    private DomainExtensionRepository domainExtensionRepository;

    @Override
    public void deleteInBatch(List<DomainExtension> domainExtensions) {
        domainExtensionRepository.deleteInBatch(domainExtensions);
    }

    @Override
    public void saveInBatch(List<DomainExtension> domainExtensions) {
        domainExtensionRepository.saveAll(domainExtensions);
    }

    @Override
    public List<DomainExtension> findAll() {
        return domainExtensionRepository.findAll();
    }

    @Override
    public DomainExtension findById(Long id){
        Optional<DomainExtension> res = domainExtensionRepository.findById(id);
        return res.isPresent()?res.get():null;
    }
}
