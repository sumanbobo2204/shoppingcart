package com.org.shoppingcart.service;

import com.org.shoppingcart.exception.ResourceNotFoundException;
import com.org.shoppingcart.entity.Cart;
import com.org.shoppingcart.entity.Product;

import java.util.List;
import java.util.Optional;

public interface CartService {

    Optional<Product> getProductDetailsByProductCode(final Long cartId, final String productCode) throws ResourceNotFoundException;

    Optional<Product> getProductDetailsByProductName(final Long cartId, final String productName) throws ResourceNotFoundException;

    List<Product> getBooksByProductCategory(final Long cartId);

    List<Product> getApparalsByProductCategory(final Long cartId);

    void deleteProductByIdFromCart(final Long cartId, final String productCode);

    void deleteAllProductsFromCart(final Long cartId);

    void updateProductByIdInCart(final Long cartId, final String productCode, final Integer productQuantity);

    Optional<Cart> findCartById(Long cartId) throws ResourceNotFoundException;

    void saveCart(final Cart cart);
}
