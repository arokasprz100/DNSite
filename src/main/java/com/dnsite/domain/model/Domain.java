package com.dnsite.domain.model;

import com.dnsite.record.model.Record;
import com.dnsite.zone.model.Zone;
import com.fasterxml.jackson.annotation.JsonIgnore;
import utils.CustomConstraints.CaseMode;
import utils.CustomConstraints.CheckCase;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "domains")
public class Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    @NotNull
    @CheckCase(CaseMode.LOWER)
    private String name;

    @Column(name = "master")
    private String master;

    @Column(name="last_check")
    private int lastCheck;

    @Column(name="type")
    private String type;

    @Column(name="notified_serial")
    private int notifiedSerial;

    @Column(name="account")
    private String account;

    @OneToMany(mappedBy = "domain", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Zone> zones;

    @OneToMany(mappedBy = "domain", cascade = CascadeType.ALL)
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public Set<Zone> getZones() {
        return zones;
    }

    public void setZones(Set<Zone> zones) {
        this.zones = zones;
    }

    public Set<Record> getRecords() {
        return records;
    }

    public void setRecords(Set<Record> records) {
        this.records = records;
    }
}