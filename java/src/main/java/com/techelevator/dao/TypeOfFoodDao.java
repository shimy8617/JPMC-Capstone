package com.techelevator.dao;

import com.techelevator.model.TypeOfFood;
import com.techelevator.model.User;

import java.util.List;

public interface TypeOfFoodDao {
    TypeOfFood getTypeById(int typeId);
    List<TypeOfFood> getTypeById(User userFromPrincipal);
    int getIdByType(String categoryName);

    List <TypeOfFood> getAllTypes();
}
