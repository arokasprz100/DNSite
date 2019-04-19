package com.dnsite.supermaster.repository;

import com.dnsite.supermaster.model.Supermaster;
import com.dnsite.supermaster.model.SupermasterId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupermasterRepository extends JpaRepository<Supermaster, SupermasterId> {

}
