package com.techelevator.service;

import com.techelevator.model.TypeOfFood;
import com.techelevator.model.User;

import java.util.List;

public interface TypeOfFoodService {
    //TypeOfFoodService[] allTypeOfFood = new TypeOfFoodService[13];

    List<TypeOfFood> getAllTypes(TypeOfFood typeOfFood);
    List<TypeOfFood> getTypeById(User userFromPrincipal);

}
