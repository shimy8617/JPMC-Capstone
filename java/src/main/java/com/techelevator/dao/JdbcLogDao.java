package com.techelevator.dao;

import com.techelevator.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcLogDao implements LogDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcLogDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void logError(User user, String logType, String message, Exception ex) {

    }

    @Override
    public void logAudit(User user, String auditCode, String message) {

    }
}
