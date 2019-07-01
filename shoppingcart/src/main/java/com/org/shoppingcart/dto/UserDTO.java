package com.org.shoppingcart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
    private String userName;
    private String userPassword;
    private String emailId;
    private String mobileNo;
    private String address;
    private String cartName;
}
