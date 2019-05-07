package com.dnsite.record.model;

import javax.persistence.*;

@Entity
@Table(name = "records")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /*
    @ManyToOne
    @JoinColumn
    private Domain domain = null;
    */

    // TODO: fix
    public String domain;

    private String name;

    private String type;

    private String content;

    private Long ttl;

    @Column(name = "prio")
    private Long priority;

    @Column(name = "change_date")
    private Long changeDate;

    private Boolean disabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public Long getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Long changeDate) {
        this.changeDate = changeDate;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
/*
    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }
*/

}
