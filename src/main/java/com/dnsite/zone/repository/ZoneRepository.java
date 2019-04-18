package com.dnsite.zone.repository;

import com.dnsite.zone.model.Zone;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ZoneRepository extends JpaRepository<Zone, Long> {
    Zone getZoneById(Long id);
}
