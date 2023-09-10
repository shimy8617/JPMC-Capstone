package com.techelevator.dao;

import com.techelevator.model.Restaurant;
import com.techelevator.model.TypeOfFood;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class JdbcRestaurantDao implements RestaurantDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcRestaurantDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Restaurant getRestaurantById(int restaurantId) {
        Restaurant restaurant = new Restaurant();

        String sql = "SELECT * FROM restaurant WHERE restaurant_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, restaurantId);

        if (results.next()) {
            restaurant = mapRowToRestaurant(results);
            return restaurant;
        }
        return null;

    }

    @Override
    public int getZipcodeById(int restaurantId) {
        int zipCode;
        try {
            zipCode = jdbcTemplate.queryForObject("select zip_code from restaurant where restaurant_id = ?", int.class, restaurantId);
        } catch (EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException("Restaurant ID " + restaurantId + " was not found.");
        }
        return zipCode;
    }

    @Override
    public Restaurant getRestaurantByTypeId(int typeId) {
        String sql = "SELECT * from restaurant r join restaurant_type_of_food rtf on r.restaurant_id=rtf.restaurant_id WHERE rtf.type_id = ?;";
        Map<String,Object> params = new HashMap<>();
        params.put("id",typeId);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        if (results.next()) {
            return mapRowToRestaurant(results);
        }
        return null;
    }

    public Restaurant randomRestaurant(int userId) {
        Restaurant matchingRestaurant = new Restaurant();

        String sql = "SELECT r.restaurant_id, r.restaurant_name, r.description, " +
                "r.phone_number, r.rating, r.review, r.photo_path, r.zip_code, r.home_page, r.address" +
                " FROM restaurant as r" +
                " JOIN restaurant_type_of_food as rtf ON rtf.restaurant_id = r.restaurant_id" +
                " JOIN user_type_of_food as utf ON utf.type_id = rtf.type_id" +
                " JOIN users as u ON utf.user_id = u.user_id" +
                " WHERE utf.user_id = ? AND r.zip_code = u.zip_code ORDER BY RANDOM() LIMIT 1";
//        Map<String,Object> params = new HashMap<>();
//        params.put("limit",userId);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        if(results.next()) {
            matchingRestaurant = mapRowToRestaurant(results);
        }

        return matchingRestaurant;
    }



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
