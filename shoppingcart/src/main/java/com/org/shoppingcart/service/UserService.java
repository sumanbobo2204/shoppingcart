package com.org.shoppingcart.service;

import com.org.shoppingcart.entity.User;
import com.org.shoppingcart.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public User createUser(final User user);

    public List<User> fetchAllUsers();

    public Optional<User> findByUserName(String userName) throws ResourceNotFoundException;


}
