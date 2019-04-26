package com.dnsite.zone.service;

import com.dnsite.zone.model.Zone;
import com.dnsite.zone.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZoneServiceImpl implements ZoneService{

    @Autowired
    private ZoneRepository zoneRepository;

    @Override
    public void deleteInBatch(List<Zone> zones) {
        zoneRepository.deleteInBatch(zones);
    }

    @Override
    public void saveInBatch(List<Zone> zones) {
        zoneRepository.saveAll(zones);
    }

    @Override
    public List<Zone> findAll() {
        return zoneRepository.findAll();
    }
}
