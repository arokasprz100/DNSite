package com.dnsite.domain.DTOs;

import com.dnsite.domain.model.Domain;

public class DomainDTOToDomainConverter {

    public static Domain convert(DomainDTO domainFromClient) {
        Domain domain = new Domain();

        domain.setId(domainFromClient.getId());
        domain.setType(domainFromClient.getType());
        domain.setName(domainFromClient.getName());
        domain.setLastCheck(domainFromClient.getLastCheck());
        domain.setAccount(domainFromClient.getAccount());
        domain.setNotifiedSerial(domainFromClient.getNotifiedSerial());
        domain.setMaster(domainFromClient.getMaster());

        return domain;
    }
}
