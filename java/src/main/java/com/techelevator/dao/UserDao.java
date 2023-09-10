package com.techelevator.dao;

import com.techelevator.model.Restaurant;
import com.techelevator.model.TypeOfFood;
import com.techelevator.model.User;

import java.util.List;

public interface UserDao {

    List<User> findAll();

    User getUserById(int userId);

    User findByUsername(String username);

    int findIdByUsername(String username);
    void updatePassword(String userName, String password, String salt);
    User getUserByUserName(String userName, boolean returnPassword);
    User saveUser(User user);
    void deleteUser(int id);
    List<User> getUsers(User currentUser);
    List<TypeOfFood> userPreferences(User user);
    boolean saveUserPreferences(int typeId, int userId);
    boolean createUser(String username, String password, String role, String fullName, String email, int zipCode, String address);
    boolean saveUserLike(int userId, int restaurantId);
    void deleteUserPreferences (int userId);
    List <Restaurant> getUserLikes (User user);

}
