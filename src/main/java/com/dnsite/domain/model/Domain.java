package com.dnsite.domain.model;

import com.dnsite.domainExtension.model.DomainExtension;
import com.dnsite.record.model.Record;
import com.dnsite.utils.CustomConstraints.CaseMode;
import com.dnsite.utils.CustomConstraints.CheckCase;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import com.dnsite.record.model.Record;

@Entity
@Table(name = "domains", indexes = {
        @Index(columnList = "name", name="name_index")
})
public class Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public enum TYPE{NATIVE,MASTER,SLAVE;

        public int getValue() {
            return this.ordinal();
        }

        public static TYPE forValue(int value) {
            return values()[value];
        }

        public String toString() {
            return forValue(getValue()).name();
        }
    }

    public final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TYPE type = TYPE.NATIVE;

    @Column(name = "name")
    @CheckCase(CaseMode.LOWER)
    @NotNull
    private String name;

    @Column(name = "master")
    private String master;

    @Column(name = "last_check")
    private int lastCheck;

    @Column(name = "notified_serial")
    private int notifiedSerial = Integer.parseInt(dateFormat.format(new Date()))*100;

    @Column(name = "account")
    private String account;

    @OneToOne(mappedBy = "domain", cascade = CascadeType.ALL)
    @JsonIgnore
    private DomainExtension domainExtensions;

    @OneToMany( mappedBy = "domain", cascade = CascadeType.ALL)
    @JsonIgnore

    private Set<Record> records;

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

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public int getNotifiedSerial() {
        return notifiedSerial;
    }

    public void setNotifiedSerial(int notifiedSerial) {
        this.notifiedSerial = notifiedSerial;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public DomainExtension getDomainExtensions() {
        return domainExtensions;
    }

    public void setDomainExtensions(DomainExtension domainExtensions) {
        this.domainExtensions = domainExtensions;
    }

    public Set<Record> getRecords() {
        return records;
    }

    public void setRecords(Set<Record> records) {
        this.records.clear();
        this.records.addAll(records);
    }

    public void addRecord(Record record) {
        records.add(record);
        record.setDomain(this);
    }

    public void removeRecord(Record record) {
        records.remove(record);
        record.setDomain(null);
    }

}