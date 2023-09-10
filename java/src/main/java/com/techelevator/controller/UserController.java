package com.techelevator.controller;

import com.techelevator.model.*;
import com.techelevator.service.LogService;
import com.techelevator.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController extends BaseController {
    private final UserService userHandler;

    public UserController(UserService userHandler, LogService logService){
        super(logService);
        this.userHandler = userHandler;
    }
    @RequestMapping(value="/login", method = RequestMethod.POST)
    public LoginResponseDto login(@Valid @RequestBody LoginDto loginDto) {
        LoginResponseDto response = userHandler.login(loginDto);
        if (response != null) return response;
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username and password are incorrect.");
    }
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public User register(@Valid @RequestBody RegisterUserDto registerUserDto) {
        return userHandler.registerUser(registerUserDto);
    }

    @GetMapping(value = "/user/current",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public User getCurrentOrBlankUser(Principal principal) {
        User currentUser = super.getUserFromPrincipal(principal);
        if (currentUser != null) {
            throw new IllegalArgumentException("no current user");
        }
        return currentUser;
    }

    @PutMapping(value = "/user",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public User updateUser(@RequestBody User user, Principal principal) {
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No current user.");
        }
        User currentUser = super.getUserFromPrincipal(principal);

        User newlyAddedUser = this.userHandler.saveUser(currentUser,user);
        if (newlyAddedUser != null) {
            return newlyAddedUser;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User update failed.");
    }

    @GetMapping(value = "/user/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public User getUser(@PathVariable int id, Principal principal) {
        User currentUser = super.getUserFromPrincipal(principal);

        User searchedUser = this.userHandler.getUserById(currentUser,id);
        if (searchedUser != null) {
            return searchedUser;
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User update failed.");
    }
    @PostMapping(value = "/userSearch",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('Admin')")
    public List<User> getUsers(@RequestBody UserSearchCriteria search, Principal principal) {
        User currentUser = super.getUserFromPrincipal(principal);

        List<User> searchedUsers = this.userHandler.getUsers(currentUser);
        if (searchedUsers != null) {
            return searchedUsers;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to return.");
    }

    @PostMapping(value = "/likes/{restaurantId}", produces = MediaType.APPLICATION_JSON_VALUE)
     public void storeLikes (@Valid @PathVariable int restaurantId, Principal principal){

        userHandler.storeUserLike(restaurantId, super.getUserFromPrincipal(principal));
    }


    @GetMapping(value = "/myLikes",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public List <Restaurant> getMyLikes(Principal principal) {
        return this.userHandler.getUserLikesList(super.getUserFromPrincipal(principal));
    }


   @GetMapping ("/myPreferences")
    public List<TypeOfFood> getUserPreferences(User user) {
        return this.userHandler.userPreferences(user);
    }

    @PostMapping (value = "/updatePreferences",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public void updateUserPreferences(@RequestBody PreferencesDto preferencesDto) {
        userHandler.updateUserPreferences(preferencesDto);

    }


}
