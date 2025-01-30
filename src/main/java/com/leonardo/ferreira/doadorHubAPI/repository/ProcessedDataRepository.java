package com.leonardo.ferreira.doadorHubAPI.repository;

import com.leonardo.ferreira.doadorHubAPI.entity.ProcessedDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedDataRepository extends JpaRepository<ProcessedDataEntity, String> {}
