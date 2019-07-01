package com.org.shoppingcart.repository;

import com.org.shoppingcart.entity.Cart;
import com.org.shoppingcart.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Product> getProductDetailsByProductCode(final Long cartId, final String productCode);

    Optional<Product> getProductDetailsByProductName(final Long cartId, final String productName);

    List<Product> getBooksByProductCategory(final Long cartId);

    List<Product> getApparalsByProductCategory(final Long cartId);

    @Transactional
    @Modifying
    void deleteAllProductsFromCart(final Long cartId);

    @Transactional
    @Modifying
    void deleteProductByIdFromCart(final Long cartId, final String productCode);

    @Transactional
    @Modifying
    void updateProductByIdInCart(final Long cartId, final String productCode, final Integer productQuantity);

}
