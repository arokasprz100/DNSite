package com.dnsite.supermaster.service;

import com.dnsite.supermaster.model.Supermaster;
import com.dnsite.supermaster.model.SupermasterId;

import java.util.List;

public interface SupermasterService {

    void delete(SupermasterId supermasterId);

    List<Supermaster> findAll();

    void deleteInBatch(List<Supermaster> supermasters);

    void saveInBatch(List<Supermaster> supermasters);
}
