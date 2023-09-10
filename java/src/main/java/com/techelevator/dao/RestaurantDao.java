package com.techelevator.dao;

import com.techelevator.model.Restaurant;
import com.techelevator.model.TypeOfFood;

import java.util.List;

public interface RestaurantDao {
    Restaurant getRestaurantById(int restaurantId);

    int getZipcodeById (int restaurantId);

    Restaurant getRestaurantByTypeId(int typeId);


    Restaurant randomRestaurant(int numberOfRestaurantsToReturn);


}
