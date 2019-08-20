package com.dnsite.cryptokey.model;

import com.dnsite.domain.model.Domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cryptokeys", indexes = {
        @Index(columnList = "domain_id", name="domainidindex")
})
public class CryptoKeyModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "domain_id")
    private Domain domain = null;

    @NotNull
    @Column(name = "flags")
    @NotNull
    private int flags = 0;

    @Column(name = "active")
    private boolean active = false;

    @Column(name = "content")
    private String content = "";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
