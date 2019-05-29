package com.dnsite.supermaster.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "supermasters")
public class Supermaster {

    @EmbeddedId
    private SupermasterId supermasterId;

    private String account;

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
