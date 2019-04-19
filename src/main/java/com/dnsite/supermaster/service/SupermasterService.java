package com.dnsite.supermaster.service;

import com.dnsite.supermaster.model.Supermaster;
import com.dnsite.supermaster.model.SupermasterId;

import java.util.List;

public interface SupermasterService {

    void save(Supermaster supermaster);

    void delete(SupermasterId supermasterId);

    Supermaster findById(SupermasterId supermasterId);

    List<Supermaster> findAll();
}
