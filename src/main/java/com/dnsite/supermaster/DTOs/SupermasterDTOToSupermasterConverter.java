package com.dnsite.supermaster.DTOs;

import com.dnsite.supermaster.model.Supermaster;
import com.dnsite.supermaster.model.SupermasterId;

public class SupermasterDTOToSupermasterConverter {

    public static Supermaster convert(SupermasterDTO supermasterDTO)
    {
        Supermaster supermaster = new Supermaster();
        SupermasterId supermasterId = new SupermasterId();
        supermasterId.setIp(supermasterDTO.getIp());
        supermasterId.setNameserver(supermasterDTO.getNameserver());
        supermaster.setSupermasterId(supermasterId);
        supermaster.setAccount(supermasterDTO.getAccount());

        return supermaster;
    }
}
