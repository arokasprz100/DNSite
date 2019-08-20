package com.dnsite.history.service;

import com.dnsite.history.model.History;

import java.util.List;

public interface HistoryService {

    void save(String model, String action);

    List<History> findAll();
}
