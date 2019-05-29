package com.dnsite.supermaster.service;

import com.dnsite.supermaster.model.Supermaster;
import com.dnsite.supermaster.repository.SupermasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupermasterServiceImpl implements SupermasterService {

    @Autowired
    private SupermasterRepository supermasterRepository;


    @Override
    public List<Supermaster> findAll() {
        return supermasterRepository.findAll();
    }

    @Override
    public void deleteInBatch(List<Supermaster> supermasters) {
        supermasterRepository.deleteInBatch(supermasters);
    }

    @Override
    public void saveOrUpdate(List<Supermaster> supermasters) {
        supermasterRepository.saveAll(supermasters);
    }

}
