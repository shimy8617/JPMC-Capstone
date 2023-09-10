package com.techelevator.service;

import com.techelevator.model.Restaurant;
import com.techelevator.model.User;

import java.security.Principal;
import java.util.List;

public interface RestaurantService {

    Restaurant randomRestaurant(User userFromPrincipal);

    Restaurant getRestaurantById (int restaurantId, User user);
}
