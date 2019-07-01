package com.org.shoppingcart.dto;

import com.org.shoppingcart.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserResponse {

        private List<User> userList;

}
