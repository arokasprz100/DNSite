package com.dnsite.record.model;

import com.dnsite.domain.model.Domain;
import com.dnsite.utils.custom_constraints.letter_case.LetterCase;
import com.dnsite.utils.custom_constraints.letter_case.LetterCaseMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "records", indexes = {
        @Index(columnList = "name", name="rec_name_index"),
        @Index(columnList = "name, type", name="nametype_index"),
        @Index(columnList = "domain_id", name="domain_id")
})
public class Record {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="domain_id", nullable = false)
    @NotNull
    private Domain domain;

    @Column(name = "name")
    @LetterCase(LetterCaseMode.LOWER)
    private String name = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    @NotNull
    private RecordType type = null;

    @Column(name = "content")
    private String content = null;

    @Column(name = "ttl")
    private Long ttl = null;

    @Column(name = "prio")
    private Long priority = null;

    @Column(name = "disabled")
    private Boolean disabled = false;

    @Column(name = "ordername")
    private String orderName = null;

    @Column(name = "auth")
    private Boolean auth = true;

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

    public RecordType getType() {
        return type;
    }

    public void setType(RecordType type) {
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

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public Boolean getAuth() {
        return auth;
    }

    public void setAuth(Boolean auth) {
        this.auth = auth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Record )) return false;
        return id != null && id.equals(((Record) o).getId());
    }
}
