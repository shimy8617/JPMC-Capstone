package com.techelevator.controller;

import com.techelevator.service.LogService;
import com.techelevator.model.TypeOfFood;
import com.techelevator.service.TypeOfFoodService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
//@PreAuthorize("isAuthenticated()")
public class TypeOfFoodController {

    private final TypeOfFoodService typeOfFoodHandler;

    public TypeOfFoodController(TypeOfFoodService typeOfFoodHandler, LogService logService) {
//        super(logService);
        this.typeOfFoodHandler = typeOfFoodHandler;
    }
    @GetMapping ("/preferences/all")

    public List<TypeOfFood> getAllTypes(TypeOfFood typeOfFood) {
        return this.typeOfFoodHandler.getAllTypes(typeOfFood);
    }


}
