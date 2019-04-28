package com.dnsite.domain.repository;

import com.dnsite.domain.model.Domain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DomainRepository extends JpaRepository<Domain, Long> {

}
