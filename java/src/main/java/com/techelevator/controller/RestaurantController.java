package com.techelevator.controller;

import com.techelevator.model.Restaurant;
import com.techelevator.service.LogService;
import com.techelevator.service.RestaurantService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
//@PreAuthorize("isAuthenticated()")
public class RestaurantController extends BaseController {
    private final RestaurantService restaurantHandler;

    public RestaurantController(RestaurantService restaurantHandler, LogService logService) {
        super(logService);
        this.restaurantHandler = restaurantHandler;
    }

@GetMapping(value = "/restaurant/random",
        produces = MediaType.APPLICATION_JSON_VALUE)

public Restaurant randomRestaurant(Principal principal) {
    return this.restaurantHandler.randomRestaurant(super.getUserFromPrincipal(principal));
}

@GetMapping (value = "/restaurant/detail/{restaurantId}", produces = MediaType.APPLICATION_JSON_VALUE)

    // Principal principal como param?
    public Restaurant getRestaurantById (@PathVariable int restaurantId, Principal principal){
        return this.restaurantHandler.getRestaurantById(restaurantId, super.getUserFromPrincipal(principal));
}

}
