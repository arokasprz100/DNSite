package com.dnsite.supermaster.DTOs;

import com.dnsite.supermaster.model.Supermaster;

public class SupermasterDTO {

    private String ip;

    private String nameserver;

    private String account;

    private Long tableIndex;

    public SupermasterDTO() {}

    public SupermasterDTO(Supermaster supermaster) {
        ip = supermaster.getSupermasterId().getIp().getAddress();
        nameserver = supermaster.getSupermasterId().getNameserver();
        account = supermaster.getAccount();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNameserver() {
        return nameserver;
    }

    public void setNameserver(String nameserver) {
        this.nameserver = nameserver;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Long getTableIndex() {
        return tableIndex;
    }

    public void setTableIndex(Long tableIndex) {
        this.tableIndex = tableIndex;
    }
}
