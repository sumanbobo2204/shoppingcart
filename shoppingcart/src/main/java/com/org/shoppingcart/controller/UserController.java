package com.org.shoppingcart.controller;

import com.org.shoppingcart.entity.User;
import com.org.shoppingcart.exception.ResourceNotFoundException;
import com.org.shoppingcart.dto.UserDTO;
import com.org.shoppingcart.dto.UserResponse;
import com.org.shoppingcart.service.UserService;
import com.org.shoppingcart.util.DtoConverter;
import com.org.shoppingcart.util.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Log4j2
@Api(value="Shopping cart System", description="Operations pertaining to user in shopping cart System")
public class UserController {

    final UserService userService;

    final Validator validator;

    @ApiOperation(value = "Creating a user", response = String.class)
    @PostMapping("/create")
    public String createUser(@RequestBody final UserDTO userDTO) {
        log.info("Request for create user : " +userDTO);
        validator.validateUser(userDTO);
        userService.createUser(DtoConverter.convertDtoToUserEntity(userDTO));
        return "User " +userDTO.getUserName()+ " created ";
    }

    @ApiOperation(value = "Get all the users created in the system", response = UserResponse.class)
    @GetMapping("/getAll")
    public UserResponse getAllUsers(){
        List<User> users = userService.fetchAllUsers();
        UserResponse userResponse = new UserResponse();
        userResponse.setUserList(users);
        return userResponse;
    }

    @ApiOperation(value = "Getting a specific user by userName", response = User.class)
    @GetMapping("/{userName}")
    public Optional<User> getUserByName(@PathVariable final String userName) throws ResourceNotFoundException {
        log.info("Fetching user for name : + " +userName);
       return userService.findByUserName(userName);
    }


}
