package com.dnsite.record.DTOs;

import com.dnsite.domain.service.DomainService;
import com.dnsite.record.model.Record;

public class RecordDTOToRecordConverter {

    public static Record convert(RecordDTO recordDTO, DomainService domainService) {
        Record record = new Record();

        record.setId(recordDTO.getId());
        record.setDomain(domainService.findById(recordDTO.getDomainId()));
        record.setName(recordDTO.getName());
        record.setType(recordDTO.getType());
        record.setContent(recordDTO.getContent());
        record.setTtl(recordDTO.getTtl());
        record.setPriority(recordDTO.getPriority());
        record.setChangeDate(recordDTO.getChangeDate());
        record.setDisabled(recordDTO.getDisabled());

        return record;
    }
}
