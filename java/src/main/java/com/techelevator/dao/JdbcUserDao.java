package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.techelevator.model.PreferencesDto;
import com.techelevator.model.Restaurant;
import com.techelevator.model.TypeOfFood;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.techelevator.model.User;

@Component
public class JdbcUserDao implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int findIdByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");

        int userId;
        try {
            userId = jdbcTemplate.queryForObject("select user_id from users where username = ?", int.class, username);
        } catch (EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException("User " + username + " was not found.");
        }

        return userId;
    }

    @Override
    public void updatePassword(String userName, String password, String salt) {

    }

    @Override
    public User getUserByUserName(String userName, boolean returnPassword) {
        return null;
    }

    @Override
    public User saveUser(User user) {
        return null;
    }

    @Override
    public void deleteUser(int id) {

    }

    @Override
    public List<User> getUsers(User currentUser) {
        return null;
    }


    @Override
	public User getUserById(int userId) {
		String sql = "SELECT * FROM users WHERE user_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
		if (results.next()) {
            User user = mapRowToUser(results);
			return getUserTypeOfFood(user);
		} else {
			return null;
		}
	}
    @Override
    public List<TypeOfFood> userPreferences(User user) {
        String sql = "SELECT utf.type_id FROM user_type_of_food as utf" +
                "JOIN users as u ON u.user_id = utf.user_id" +
                "JOIN type_of_food as tf ON tf.type_id = utf.type_id" +
                "WHERE utf.user_id = ?";
        List<TypeOfFood> typeOfFoodList = new ArrayList<>();
        user.setTypeOfFood(typeOfFoodList);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user.getId());
        while (results.next()) {
            typeOfFoodList.add(mapRowToTypeOfFood(results));
        }
        return typeOfFoodList;
    }

    @Override
    public List<Restaurant> getUserLikes(User user) {
        String sql = "SELECT * from restaurant r join user_likes ul on r.restaurant_id = ul.restaurant_id WHERE user_id = ?";
        List<Restaurant> userLikesList = new ArrayList<>();
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user.getId());
        while (results.next()) {
            userLikesList.add(mapRowToRestaurant(results));
        }
        return userLikesList;

    }

    @Override
    public boolean saveUserPreferences(int userId, int typeId) {
        String sql = "insert into user_type_of_food (user_id, type_id) values (?,?)";
        return jdbcTemplate.update(sql, userId, typeId) == 1;
    }

    @Override
    public void deleteUserPreferences(int userId) {
        String sql = "delete from user_type_of_food where user_id = ?";
        jdbcTemplate.update(sql, userId);
    }

    @Override
    public boolean saveUserLike(int userId, int restaurantId) {
        String sql = "insert into user_likes (user_id, restaurant_id) values (?,?)";
        return jdbcTemplate.update(sql, userId, restaurantId) == 1;
    }


    //ACA LO TOMAMOS DE LA BASE DE DATOS
    public User getUserTypeOfFood(User user) {
        String sql = "select tf.type_id, tf.categories_name " +
                "from user_type_of_food utf " +
                "join type_of_food tf on utf.type_id = tf.type_id " +
                "where utf.user_id = ?";
        List<TypeOfFood> typeOfFoodList = new ArrayList<>();
        user.setTypeOfFood(typeOfFoodList);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user.getId());
        while (results.next()) {
            typeOfFoodList.add(mapRowToTypeOfFood(results));
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "select * from users";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            User user = mapRowToUser(results);
            //cargar user preferences (ver codigo)
            user = getUserTypeOfFood(user);
            users.add(user);

        }

        return users;
    }

    @Override
    public User findByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");

        for (User user : this.findAll()) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        throw new UsernameNotFoundException("User " + username + " was not found.");
    }

    @Override
    public boolean createUser(String username, String password, String role, String fullName,
                              String email, int zipCode, String address) {
        String insertUserSql = "insert into users (username,password_hash,role," +
                "full_name,email,zip_code,address) values (?,?,?,?,?,?,?)";
        String password_hash = new BCryptPasswordEncoder().encode(password);
        String ssRole = role.toUpperCase().startsWith("ROLE_") ? role.toUpperCase() : "ROLE_" + role.toUpperCase();

        return jdbcTemplate.update(insertUserSql, username, password_hash, ssRole, fullName, email, zipCode, address) == 1;
    }


    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password_hash"));
        user.setAuthorities(Objects.requireNonNull(rs.getString("role")));
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString("email"));
        user.setZipCode(rs.getInt("zip_code"));
        user.setAddress(rs.getString("address"));
        user.setActivated(true);
        return user;
    }
    private TypeOfFood mapRowToTypeOfFood(SqlRowSet rs) {
        TypeOfFood typeOfFood = new TypeOfFood();
        typeOfFood.setTypeId(rs.getInt("type_id"));
        typeOfFood.setCategoryName(rs.getString("categories_name"));
        return typeOfFood;
    }

//    private PreferencesDto mapRowToPreferences(SqlRowSet rs) {
//        PreferencesDto preferencesDto = new PreferencesDto();
//        preferencesDto.setUserId(rs.getInt("user_id"));
//        preferencesDto.setTypeId(rs.getInt("type_id"));
//        return preferencesDto;
//    }

    private Restaurant mapRowToRestaurant(SqlRowSet rs) {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(rs.getInt("restaurant_id"));
        restaurant.setRestaurantName(rs.getString("restaurant_name"));
        restaurant.setDescription(rs.getString("description"));
        restaurant.setPhoneNumber(rs.getString("phone_number"));
        restaurant.setRating(rs.getInt("rating"));
        restaurant.setReview(rs.getString("review"));
        restaurant.setPhotoPath(rs.getString("photo_path"));
        restaurant.setZipCode(rs.getInt("zip_code"));
        restaurant.setHomePage(rs.getString("home_page"));
        restaurant.setAddress(rs.getString("address"));



        return restaurant;
    }
}
