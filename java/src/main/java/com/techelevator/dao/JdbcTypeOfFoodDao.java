package com.techelevator.dao;

import com.techelevator.model.TypeOfFood;
import com.techelevator.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JdbcTypeOfFoodDao implements TypeOfFoodDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTypeOfFoodDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public TypeOfFood getTypeById(int typeId) {
        String sql = "SELECT * FROM type_of_food WHERE type_id = ?";
        Map<String,Object> params = new HashMap<>();
        params.put("id",typeId);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        if (results.next()) {
            return mapRowToTypeOfFood(results);
        }
        return null;
    }

    @Override
    public List<TypeOfFood> getTypeById(User userFromPrincipal) {
        return null;
    }

    @Override
    public int getIdByType(String categoryName) {
        if (categoryName == null) throw new IllegalArgumentException("Category name cannot be null");

        int typeId;
        try {
            typeId = jdbcTemplate.queryForObject("select type_id from type_of_food where categories_name = ?", int.class, categoryName);
        } catch (EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException("User " + categoryName + " was not found.");
        }

        return typeId;
    }

    @Override
    public List<TypeOfFood> getAllTypes() {
        List<TypeOfFood> typeOfFoodList = new ArrayList<>();
        String sql = "select * from type_of_food";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            TypeOfFood typeOfFood = mapRowToTypeOfFood(results);
            typeOfFoodList.add(typeOfFood);
        }
        return typeOfFoodList;
    }

    private TypeOfFood mapRowToTypeOfFood(SqlRowSet rs) {
        TypeOfFood typeOfFood = new TypeOfFood();
        typeOfFood.setTypeId(rs.getInt("type_id"));
        typeOfFood.setCategoryName(rs.getString("categories_name"));

        return typeOfFood;
    }
}
