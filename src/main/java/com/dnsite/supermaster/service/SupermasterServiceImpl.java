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

    private SupermasterRepository supermasterRepository;

    @Autowired
    SupermasterServiceImpl(SupermasterRepository supermasterRepository){
        this.supermasterRepository = supermasterRepository;
    }

    @Override
    public void save(Supermaster supermaster) {
        supermasterRepository.save(supermaster);
    }

    @Override
    public void delete(SupermasterId supermasterId) {
        supermasterRepository.deleteById(supermasterId);
    }

    @Override
    public Supermaster findById(SupermasterId supermasterId) {
        Optional<Supermaster> supermasterOptional = supermasterRepository.findById(supermasterId);
        return supermasterOptional.orElse(null);
    }

    @Override
    public List<Supermaster> findAll() {
        return supermasterRepository.findAll();
    }

}
