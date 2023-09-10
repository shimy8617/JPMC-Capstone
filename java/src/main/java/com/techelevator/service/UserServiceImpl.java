package com.techelevator.service;

import com.techelevator.dao.UserDao;
import com.techelevator.model.*;
import com.techelevator.security.jwt.TokenProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService{
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserDao userDao;

    public UserServiceImpl(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder,
                           UserDao userDao) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userDao = userDao;
    }
    @Override
    public LoginResponseDto login(LoginDto loginDto) {

        try {
            User user = userDao.findByUsername(loginDto.getUsername());

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);


            /////////////// VER SI REMEMBER ME ES TRUE
            String jwt = tokenProvider.createToken(authentication, false,String.valueOf(user.getId()));

            return new LoginResponseDto(jwt, user);
        } catch (UsernameNotFoundException ex) {
            System.out.println("Username not found: " + loginDto.getUsername());
        } catch (AuthenticationException ex) {
            System.out.println("Authentication failed");
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean createUser(RegisterUserDto newUser) {
        return false;
    }

    @Override
    public List<User> getAllUsers(User user) {
        return null;
    }

    @Override
    public User getCurrent(User user) {
        return null;
    }

    @Override
    public User getUserById(User callingUser, int id) {
        if (callingUser.isMemberOfRole("Admin")) {
            return userDao.getUserById(id);
        } else if (callingUser.getId()==id) {
            return userDao.getUserById(id);
        }
        return null;
    }

    @Override
    public boolean updateUserPassword(User callingUser, User userToChangePassword){
        if (!userToChangePassword.getPassword().equals(userToChangePassword.getConfirmPassword())){
            throw new RuntimeException("Passwords do not match");
        }
        if (callingUser.isMemberOfRole("Admin")) {
            userDao.updatePassword(userToChangePassword.getUsername(), userToChangePassword.getPassword(), null);
        } else if (callingUser.getId()==userToChangePassword.getId()) {
            userDao.updatePassword(userToChangePassword.getUsername(), userToChangePassword.getPassword(), null);
        }
        return true;
    }
    @Override
    public User registerUser(RegisterUserDto newUser){
        if ((newUser==null)) {
            throw new RuntimeException("user is null");
        }
        if (userDao.getUserByUserName(newUser.getUsername(),false)!=null){
            throw new RuntimeException("Username already exists");
        }
        return userDao.saveUser(newUser.toUser());

    }
    @Override
    public void deleteUser(User user){
        userDao.deleteUser(user.getId());
    }
    @Override
    public User saveUser(User callingUser, User newUser){
        if ((callingUser==null)||(callingUser.equals(newUser))) {
            return userDao.saveUser(newUser);
        }
        if (callingUser.isMemberOfRole("Admin")) {
            return userDao.saveUser(newUser);
        }
        throw new RuntimeException("No permission to add user");
    }
    @Override
    public void storeUserPreferences(User user, RegisterUserDto registerUserDto) {
        List<Integer> typeIdList = registerUserDto.getPreferencesArray();
        if( typeIdList == null) {
            return;
        }
        for(int typeId : typeIdList) {
            userDao.saveUserPreferences(user.getId(), typeId);
        }

    }

    @Override
    public void storeUserLike(int restaurantId, User user) {
        userDao.saveUserLike(user.getId(), restaurantId);
    }

    @Override
    public List<Restaurant> getUserLikesList(User user) {
        return userDao.getUserLikes(user);
    }

    @Override
    public List<User> getUsers(User callingUser) {
        return null;
    }


    @Override
    public void deleteUser(User callingUser, int id) {
        if (callingUser.isMemberOfRole("Admin")) {
            userDao.deleteUser(id);
        } else if (callingUser.getId()==id) {
            userDao.deleteUser(id);
        }
    }
//change to getUserPreferences
    @Override
    public List<TypeOfFood> userPreferences(User user) {
        return userDao.userPreferences(user);
    }

    @Override
    public void updateUserPreferences(PreferencesDto preferencesDto) {
        List<Integer> typeIdList = preferencesDto.getTypeIdList();
        if( typeIdList == null) {
            return;
        }
        userDao.deleteUserPreferences(preferencesDto.getUserId());
        for(int typeId : typeIdList) {
            userDao.saveUserPreferences(preferencesDto.getUserId(), typeId);
        }
    }


}
