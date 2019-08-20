package com.dnsite.supermaster.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "supermasters")
public class Supermaster {

    @Valid
    @EmbeddedId
    private SupermasterId supermasterId;

    @Column(name = "account")
    @NotNull
    private String account = "DNSite";

    public SupermasterId getSupermasterId() {
        return supermasterId;
    }

    public void setSupermasterId(SupermasterId supermasterId) {
        this.supermasterId = supermasterId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

}
