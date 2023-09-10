package com.techelevator.service;

import com.techelevator.dao.RestaurantDao;
import com.techelevator.model.Restaurant;
import com.techelevator.model.User;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantDao restaurantDao;

    public RestaurantServiceImpl(RestaurantDao restaurantDao) {
        this.restaurantDao = restaurantDao;
    }

    @Override
    public Restaurant randomRestaurant(User userFromPrincipal) {
        return restaurantDao.randomRestaurant(userFromPrincipal.getId());
    }

    @Override
    public Restaurant getRestaurantById(int restaurantId, User user) {
        return restaurantDao.getRestaurantById(restaurantId);
    }


}
