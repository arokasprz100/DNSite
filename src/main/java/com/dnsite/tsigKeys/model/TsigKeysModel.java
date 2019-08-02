package com.dnsite.tsigKeys.model;

import com.dnsite.utils.CustomConstraints.CaseMode;
import com.dnsite.utils.CustomConstraints.CheckCase;

import javax.persistence.*;

@Entity
@Table(name = "tsigkeys", indexes = {
        @Index(columnList = "name,algorithm", name="namealgoindex"),
})
public class TsigKeysModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @CheckCase(CaseMode.LOWER)
    @Column(name = "name")
    private String name = "";

    @Column(name = "algorithm")
    private String algorithm = "";

    @Column(name = "secret")
    private String secret = "";

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
