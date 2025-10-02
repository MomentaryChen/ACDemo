package com.example.moty.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moty.demo.model.po.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}