package com.dnsite.zone.service;

import com.dnsite.zone.model.Zone;
import com.dnsite.zone.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZoneServiceImpl implements ZoneService{

    private ZoneRepository zoneRepository;

    @Autowired
    ZoneServiceImpl(ZoneRepository zoneRepository){
        this.zoneRepository = zoneRepository;
    }

    @Override
    public void save(Zone zone) {
        zoneRepository.save(zone);
    }

    @Override
    public Zone findById(Long id) {
        return zoneRepository.getZoneById(id);
    }

    @Override
    public List<Zone> listAll(){
        return zoneRepository.findAll();
    }
}
