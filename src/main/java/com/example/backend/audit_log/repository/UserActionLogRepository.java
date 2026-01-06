package com.example.backend.audit_log.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.audit_log.entity.UserActionLog;

public interface UserActionLogRepository
        extends JpaRepository<UserActionLog, Long> {
}
