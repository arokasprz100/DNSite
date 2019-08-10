package com.dnsite.record.DTOs;

import com.dnsite.record.model.Record;
import com.dnsite.record.model.RecordType;

public class RecordDTO {

    private Long id;

    private Long domainId;

    private String name;

    private RecordType type;

    private String content;

    private Long ttl;

    private Long priority;

    private Long changeDate;

    private Boolean disabled;

    private Long tableIndex;

    public RecordDTO() {}

    public RecordDTO(Record record) {
        id = record.getId();
        domainId = record.getDomain().getId();
        name = record.getName();
        type = record.getType();
        content = record.getContent();
        ttl = record.getTtl();
        priority = record.getPriority();
        changeDate = record.getChangeDate();
        disabled = record.getDisabled();
    }

    public Long getId() {
        return id;
    }

    public Long getDomainId() {
        return domainId;
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

    public Long getChangeDate() {
        return changeDate;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public Long getTableIndex() { return tableIndex; }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDomainId(Long domainId) {
        this.domainId = domainId;
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

    public void setChangeDate(Long changeDate) {
        this.changeDate = changeDate;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public void setTableIndex(Long tableIndex) { this.tableIndex = tableIndex; }
}
