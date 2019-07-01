package com.org.shoppingcart.util;

import com.org.shoppingcart.dto.ProductDTO;
import com.org.shoppingcart.dto.UserDTO;
import com.org.shoppingcart.entity.*;

import java.util.ArrayList;
import java.util.List;

public class DtoConverter {

    private static final String BOOK = "BOOK";

    private static final String APPARAL = "APPARAL";

    public static User convertDtoToUserEntity(final UserDTO userDTO) {
        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setUserPassword(userDTO.getUserPassword());
        user.setAddress(userDTO.getAddress());
        user.setEmailId(userDTO.getEmailId());
        user.setMobileNo(userDTO.getMobileNo());

        Cart cart = new Cart();
        cart.setCartName(userDTO.getCartName());
        user.setCart(cart);
        return  user;
    }


    public static Product convertProductDtoToEntity(ProductDTO productDTO) {
        switch (productDTO.getProductType()) {
            case BOOK:
                Book book = new Book();
                book.setProdName(productDTO.getProdName());
                book.setProductCode(productDTO.getProductCode());
                book.setProductQuantity(productDTO.getProductQuantity());
                book.setProdPrice(productDTO.getProdPrice());
                book.setAuthour(productDTO.getBookAuthour());
                book.setPublications(productDTO.getBookPublications());
                book.setGenre(productDTO.getBookGenre());
                return book;

            case APPARAL:
                Apparal apparal = new Apparal();
                apparal.setProdName(productDTO.getProdName());
                apparal.setProductCode(productDTO.getProductCode());
                apparal.setProductQuantity(productDTO.getProductQuantity());
                apparal.setProdPrice(productDTO.getProdPrice());
                apparal.setBrand(productDTO.getApparalBrand());
                apparal.setType(productDTO.getApparalType());
                apparal.setDesign(productDTO.getApparalDesign());
                return apparal;
        }
        return null;
    }



}
