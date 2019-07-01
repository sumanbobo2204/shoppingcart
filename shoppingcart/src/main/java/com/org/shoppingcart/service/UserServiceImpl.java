package com.org.shoppingcart.service;

import com.org.shoppingcart.entity.User;
import com.org.shoppingcart.exception.ResourceNotFoundException;
import com.org.shoppingcart.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;

    @Override
    public User createUser(final User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> fetchAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByUserName(String userName) throws ResourceNotFoundException {

        return Optional.ofNullable(userRepository.findByUserName(userName).orElseThrow(
                () -> new ResourceNotFoundException("User not found with name " +userName)));
    }
}
