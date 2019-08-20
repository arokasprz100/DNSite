package com.dnsite.record.model;


public enum RecordType
{
    A,
    AAAA,
    CAA,
    CNAME,
    HINFO,
    LOC,
    MX,
    NAPTR,
    NS,
    PTR,
    RP,
    SOA,
    SPF,
    SRV,
    TXT,
    MBOXFW;

    public int getValue() {
        return this.ordinal();
    }

    public static RecordType forValue(int value) {
        return values()[value];
    }

    public String toString() {
        return forValue(getValue()).name();
    }
}
