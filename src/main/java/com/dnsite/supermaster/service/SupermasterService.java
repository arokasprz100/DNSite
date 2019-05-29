package com.dnsite.supermaster.service;

import com.dnsite.supermaster.model.Supermaster;

import java.util.List;

public interface SupermasterService {

    List<Supermaster> findAll();

    void deleteInBatch(List<Supermaster> supermasters);

    void saveOrUpdate(List<Supermaster> supermasters);
}
