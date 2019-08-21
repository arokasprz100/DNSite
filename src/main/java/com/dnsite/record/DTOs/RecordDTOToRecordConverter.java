package com.dnsite.record.DTOs;

import com.dnsite.domain.service.DomainService;
import com.dnsite.record.model.Record;
import com.dnsite.utils.DTOs.DomainIdExtractor;

public class RecordDTOToRecordConverter {

    public static Record convert(RecordDTO recordDTO, DomainService domainService) {
        Record record = new Record();

        record.setId(recordDTO.getId());
        record.setDomain(domainService.findById(DomainIdExtractor.extract(recordDTO.getDomainInfo())));
        record.setName(recordDTO.getName());
        record.setType(recordDTO.getType());
        record.setContent(recordDTO.getContent());
        record.setTtl(recordDTO.getTtl());
        record.setPriority(recordDTO.getPriority());
        record.setDisabled(recordDTO.getDisabled());
        record.setOrderName(recordDTO.getOrderName());
        record.setAuth(recordDTO.getAuth());

        return record;
    }

}
