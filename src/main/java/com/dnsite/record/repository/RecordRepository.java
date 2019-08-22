package com.dnsite.record.repository;

import com.dnsite.record.model.Record;
import com.dnsite.record.model.RecordType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {

    List<Record> findByDomain_Id(Long domainId);

    List<Record> findByTypeAndDomain_Id(RecordType type, Long domainId);
}
