package com.dnsite.supermaster.service;

import com.dnsite.history.service.HistoryService;
import com.dnsite.supermaster.model.Supermaster;
import com.dnsite.supermaster.model.SupermasterId;
import com.dnsite.supermaster.repository.SupermasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupermasterServiceImpl implements SupermasterService {

    @Autowired
    private SupermasterRepository supermasterRepository;

    @Autowired
    private HistoryService historyService;

    @Override
    public void delete(SupermasterId supermasterId) {
        supermasterRepository.deleteById(supermasterId);
        historyService.save("SUPERMASTER","DELETE");
    }

    @Override
    public List<Supermaster> findAll() {
        return supermasterRepository.findAll();
    }

    @Override
    public void deleteInBatch(List<Supermaster> supermasters) {
        supermasterRepository.deleteInBatch(supermasters);
        historyService.save("SUPERMASTER","DELETE");
    }

    @Override
    public void saveInBatch(List<Supermaster> supermasters) {
        supermasterRepository.saveAll(supermasters);
        historyService.save("SUPERMASTER","SAVE");
    }

}
