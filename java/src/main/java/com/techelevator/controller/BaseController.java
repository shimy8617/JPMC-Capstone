package com.techelevator.controller;

import com.techelevator.exception.DaoException;
import com.techelevator.model.User;
import com.techelevator.service.LogService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.techelevator.model.ApiError;
import com.techelevator.model.UserDetail;

import java.security.Principal;

public class BaseController {
    private LogService logService;
    public BaseController(LogService logService){
        this.logService = logService;
    }
    private User user;
    //the spring security framework do this principal
    public User getUserFromPrincipal(Principal principal) {
        if (principal==null) return null;
        UserDetail userDetail = (UserDetail) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        StringBuilder builder = new StringBuilder();
        for (GrantedAuthority a: userDetail.getAuthorities()){
            builder.append(a.getAuthority()).append(",");
        }
        String auths = builder.toString();
        this.user = new User(userDetail.getId(), userDetail.getUsername(), userDetail.getPassword(), auths);
        return this.user;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DaoException.class})
    public ApiError handleTransferException(Exception exception){
        logService.logError(user,exception.getMessage(), exception);
        return new ApiError(HttpStatus.BAD_REQUEST,exception.getMessage(), exception);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class})
    public ApiError handleArgumentException(Exception exception){
        logService.logError(user,exception.getMessage(), exception);
        return new ApiError(HttpStatus.BAD_REQUEST,"Bad request", exception);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({RuntimeException.class})
    public ApiError handleRuntimeException(Exception exception){
        logService.logError(user,exception.getMessage(), exception);
        return new ApiError(HttpStatus.BAD_REQUEST,"Bad request", exception);
    }
}
