package com.dnsite.zone.service;

import com.dnsite.zone.model.Zone;

import java.util.List;

public interface ZoneService {
    void save(Zone zone);
    Zone findById(Long id);
    List<Zone> listAll();
}
