package com.dnsite.utils.DTOs;

public class DomainIdExtractor {
    public static Long extract(String domainInfo) {
        if(domainInfo == null){
            return -1L;
        }
        if (domainInfo.contains(" - ")) {
            String[] stringParts = domainInfo.split(" - ");
            return Long.parseLong(stringParts[0]);
        }
        return Long.parseLong(domainInfo);

    }
}
