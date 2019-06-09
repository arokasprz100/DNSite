package com.dnsite.domain.service;

import com.dnsite.domain.model.Domain;
import com.dnsite.domain.repository.DomainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DomainServiceImpl implements DomainService{

    @Autowired
    private DomainRepository domainRepository;

    @Override
    public void deleteInBatch(List<Domain> domains){
        domainRepository.deleteInBatch(domains);
    }

    @Override
    public void saveInBatch(List<Domain> domains) {
        domainRepository.saveAll(domains);
    }

    @Override
    public List<Domain> findAll() {
        return domainRepository.findAll();
    }

    @Override
    public Domain findById(Long id){return domainRepository.findById(id).isPresent()?domainRepository.findById(id).get():null;}
}
