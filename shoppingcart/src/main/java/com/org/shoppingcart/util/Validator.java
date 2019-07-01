package com.org.shoppingcart.util;


import com.org.shoppingcart.dto.ProductDTO;
import com.org.shoppingcart.dto.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class Validator {

    public void validateUser(final UserDTO userDTO) {
        Assert.notNull(userDTO.getAddress(), "Address can not be null");
        Assert.notNull(userDTO.getUserName(), "Username can not be null");
        Assert.notNull(userDTO.getEmailId(), "Email id can not be null");
        Assert.notNull(userDTO.getUserPassword(), "Password cant not be null");
        Assert.notNull(userDTO.getMobileNo(), "Mobile no can not be null");
    }

    public void validateProduct(final ProductDTO productDTO) {
        Assert.notNull(productDTO.getProductCode(), "Product code cannot be null");
        Assert.notNull(productDTO.getProdName(), "Product name can not be null");
        Assert.notNull(productDTO.getProdPrice(), "Product price cannot be null");
        Assert.notNull(productDTO.getProductQuantity(), "ProductQuantity can not be null");
    }


}
