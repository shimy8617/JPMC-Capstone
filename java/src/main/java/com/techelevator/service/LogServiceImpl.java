package com.techelevator.service;

import com.techelevator.dao.LogDao;
import com.techelevator.model.User;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {
    private LogDao logDao;
    public LogServiceImpl(LogDao logDao){
        this.logDao = logDao;
    }
    @Override
    public void logError(User user, String errorMessage){
        logDao.logError(user,"Error",errorMessage,null);
    }
    @Override
    public void logError(User user, String errorMessage, Exception ex){
        logDao.logError(user,"Error",errorMessage,ex);
    }
    @Override
    public void logInfo(User user, String message){
        logDao.logError(user,"Info",message,null);
    }
    @Override
    public void logAudit(User user, String auditCode, String message){
        logDao.logAudit(user,auditCode,message);
    }

}
