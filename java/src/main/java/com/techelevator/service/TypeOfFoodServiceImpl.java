package com.techelevator.service;

import com.techelevator.dao.TypeOfFoodDao;
import com.techelevator.model.TypeOfFood;
import com.techelevator.model.User;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Service
public class TypeOfFoodServiceImpl implements TypeOfFoodService {

    private final TypeOfFoodDao typeDao;

    public TypeOfFoodServiceImpl(TypeOfFoodDao typeDao) {
        this.typeDao = typeDao;
    }

    @Override
    public List<TypeOfFood> getAllTypes(TypeOfFood typeOfFood) {
        return typeDao.getAllTypes();
    }

    @Override
    public List<TypeOfFood> getTypeById(User userFromPrincipal) {
        return null;
    }

//    @Override
//    public List<TypeOfFood> getTypeById(User userFromPrincipal) {
//        return typeDao.getTypeById(userFromPrincipal.getId());
//    }
//
//    @GetMapping(value = "/movie/detail/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//
//    public TypeOfFood getTypeOfFoodById(@PathVariable int id, Principal principal) {
//        return this.typeDao.getTypeById(super.getUserFromPrincipal(principal),id,true);
//    }
}
