package com.dnsite.supermaster.service;

import com.dnsite.supermaster.model.Supermaster;
import com.dnsite.supermaster.model.SupermasterId;
import com.dnsite.supermaster.repository.SupermasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupermasterServiceImpl implements SupermasterService {

    @Autowired
    private SupermasterRepository supermasterRepository;

    @Override
    public void delete(SupermasterId supermasterId) {
        supermasterRepository.deleteById(supermasterId);
    }

    @Override
    public List<Supermaster> findAll() {
        return supermasterRepository.findAll();
    }

    @Override
    public void deleteInBatch(List<Supermaster> supermasters) {
        supermasterRepository.deleteInBatch(supermasters);
    }

    @Override
    public void saveInBatch(List<Supermaster> supermasters) {
        supermasterRepository.saveAll(supermasters);
    }

}
