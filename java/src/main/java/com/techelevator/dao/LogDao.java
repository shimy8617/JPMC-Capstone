package com.techelevator.dao;

import com.techelevator.model.User;

public interface LogDao {
    void logError(User user, String logType, String message, Exception ex);

    void logAudit(User user, String auditCode, String message);
}
