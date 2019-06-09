package com.dnsite.record.model;

import com.dnsite.domain.model.Domain;

import javax.persistence.*;


@Entity
@Table(name = "records", indexes = {
        @Index(columnList = "name", name="rec_name_index"),
        @Index(columnList = "name, type", name="nametype_index"),
        @Index(columnList = "domain_id", name="domain_id")
})
public class Record {

    public enum RTYPE{A,AAAA,CAA,CNAME,HINFO,LOC,MX,NAPTR,NS,PTR,RP,SOA,SPF,SRV,TXT,MBOXFW;
        public int getValue() {
            return this.ordinal();
        }

        public static RTYPE forValue(int value) {
            return values()[value];
        }

        public String toString() {
            return forValue(getValue()).name();
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="domain_id")
    private Domain domain = null;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private RTYPE type;

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

    public RTYPE getType() {
        return type;
    }

    public void setType(RTYPE type) {
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

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Record )) return false;
        return id != null && id.equals(((Record) o).getId());
    }
}
