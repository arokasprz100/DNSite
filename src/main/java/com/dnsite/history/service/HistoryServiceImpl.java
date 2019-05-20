package com.dnsite.history.service;

import com.dnsite.history.model.History;
import com.dnsite.history.repository.HistoryRepository;
import com.dnsite.security.service.SecurityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private SecurityServiceImpl securityService;

    @Override
    public void save(String model, String action) {
        History history = new History();
        history.setModel(model);
        history.setAction(action);
        history.setUserName(securityService.findLoggedInUsername());
        historyRepository.save(history);
    }
}
