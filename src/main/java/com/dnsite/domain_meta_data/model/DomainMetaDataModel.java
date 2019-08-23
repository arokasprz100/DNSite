package com.dnsite.domain_meta_data.model;

import com.dnsite.domain.model.Domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name ="domainmetadata", indexes = {
        @Index(columnList = "domain_id", name="domainidmetaindex"),
})
public class DomainMetaDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "domain_id")
    private Domain domain = null;

    @Column(name = "kind")
    private String kind = "";

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

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
