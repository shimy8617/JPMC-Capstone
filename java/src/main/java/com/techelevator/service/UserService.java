package com.techelevator.service;

import com.techelevator.model.*;

import java.util.List;

public interface UserService {
    LoginResponseDto login(LoginDto loginDto);
    boolean createUser(RegisterUserDto newUser);
    List<User> getAllUsers(User user);
    User getCurrent(User user);

    User getUserById(User callingUser, int id);

    boolean updateUserPassword(User callingUser, User userToChangePassword);

    User registerUser(RegisterUserDto registerUserDto);

    void deleteUser(User user);

    User saveUser(User callingUser, User newUser);
    List<User> getUsers(User callingUser);
    void deleteUser(User callingUser, int id);
    List<TypeOfFood> userPreferences(User user);
    void storeUserPreferences(User user, RegisterUserDto registerUserDto);
    void storeUserLike(int restaurantId, User user);

    List <Restaurant> getUserLikesList (User user);
    void updateUserPreferences(PreferencesDto preferencesDto);
}
