package com.dnsite.record.DTOs;

import com.dnsite.record.model.Record;
import com.dnsite.record.model.RecordType;

public class RecordDTO {

    private Long id;

    private String domainInfo;

    private String name;

    private RecordType type;

    private String content;

    private Long ttl;

    private Long priority;

    private Boolean disabled;

    private Long tableIndex;

    private String orderName;

    private Boolean auth;

    public RecordDTO() {}

    public RecordDTO(Record record) {
        id = record.getId();
        domainInfo = record.getDomain().getId() + " - " + record.getDomain().getName();
        name = record.getName();
        type = record.getType();
        content = record.getContent();
        ttl = record.getTtl();
        priority = record.getPriority();
        disabled = record.getDisabled();
        orderName = record.getOrderName();
        auth = record.getAuth();
    }

    public Long getId() {
        return id;
    }

    public String getDomainInfo() {
        return domainInfo;
    }

    public String getName() {
        return name;
    }

    public RecordType getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public Long getTtl() {
        return ttl;
    }

    public Long getPriority() {
        return priority;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public Long getTableIndex() { return tableIndex; }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDomainInfo(String domainInfo) {
        this.domainInfo = domainInfo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(RecordType type) {
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public void setTableIndex(Long tableIndex) { this.tableIndex = tableIndex; }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public Boolean getAuth() {
        return auth;
    }

    public void setAuth(Boolean auth) {
        this.auth = auth;
    }
}
