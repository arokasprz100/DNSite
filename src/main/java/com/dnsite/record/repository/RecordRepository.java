package com.dnsite.record.repository;

import com.dnsite.record.model.Record;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {

    List<Record> findByDomain_Id(Long domainId);
}
