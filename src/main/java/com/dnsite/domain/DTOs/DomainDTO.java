package com.dnsite.domain.DTOs;

import com.dnsite.domain.model.Domain;

public class DomainDTO {

    private Long id;

    private Domain.TYPE type = Domain.TYPE.NATIVE;

    private String name;

    private String master;

    private int lastCheck;

    private String account;

    private int notifiedSerial;

    private Long tableIndex;

    public DomainDTO() {}

    public DomainDTO(Domain domain) {
        id = domain.getId();
        type = domain.getType();
        name = domain.getName();
        master = domain.getMaster();
        lastCheck = domain.getLastCheck();
        account = domain.getAccount();
        notifiedSerial = domain.getNotifiedSerial();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Domain.TYPE getType() {
        return type;
    }

    public void setType(Domain.TYPE type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public int getLastCheck() {
        return lastCheck;
    }

    public void setLastCheck(int lastCheck) {
        this.lastCheck = lastCheck;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getNotifiedSerial() {
        return notifiedSerial;
    }

    public void setNotifiedSerial(int notifiedSerial) {
        this.notifiedSerial = notifiedSerial;
    }

    public Long getTableIndex() {
        return tableIndex;
    }

    public void setTableIndex(Long tableIndex) {
        this.tableIndex = tableIndex;
    }
}
