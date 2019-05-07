package com.dnsite.zone.service;

import com.dnsite.zone.model.Zone;

import java.util.List;

public interface ZoneService {
    void deleteInBatch(List<Zone> zones);
    void saveInBatch(List<Zone> zones);
    List<Zone> findAll();
}
