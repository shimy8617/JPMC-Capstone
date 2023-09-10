package com.techelevator.service;

import com.techelevator.model.User;

public interface LogService {
    void logError(User user, String errorMessage, Exception ex);
    void logError(User user, String errorMessage);
    void logInfo(User user, String message);
    void logAudit(User user, String auditCode, String message);
}
