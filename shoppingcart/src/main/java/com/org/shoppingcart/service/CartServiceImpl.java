package com.org.shoppingcart.service;

import com.org.shoppingcart.entity.Cart;
import com.org.shoppingcart.entity.Product;
import com.org.shoppingcart.exception.ResourceNotFoundException;
import com.org.shoppingcart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Log4j2
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CartServiceImpl implements CartService {

    final CartRepository cartRepository;

    @Override
    public Optional<Product> getProductDetailsByProductCode(Long cartId, String productCode) {
        return cartRepository.getProductDetailsByProductCode(cartId, productCode);
    }

    @Override
    public Optional<Product> getProductDetailsByProductName(Long cartId, String productName)
            throws ResourceNotFoundException {
        return Optional.ofNullable(cartRepository.getProductDetailsByProductName(cartId, productName)
                .orElseThrow(() -> new ResourceNotFoundException("No Product found with " +
                        "name : " +productName+ " and cartId " +cartId)));
    }

    @Override
    public List<Product> getBooksByProductCategory(Long cartId) {
        return cartRepository.getBooksByProductCategory(cartId);
    }

    @Override
    public List<Product> getApparalsByProductCategory(Long cartId) {
        return cartRepository.getApparalsByProductCategory(cartId);
    }

    @Override
    public void deleteProductByIdFromCart(Long cartId, String productCode) {
        cartRepository.deleteProductByIdFromCart(cartId, productCode);
    }

    @Override
    public void deleteAllProductsFromCart(Long cartId) {
        cartRepository.deleteAllProductsFromCart(cartId);
    }

    @Override
    public void updateProductByIdInCart(Long cartId, String productCode, Integer productQuantity) {
        cartRepository.updateProductByIdInCart(cartId, productCode, productQuantity);
    }

    @Override
    public Optional<Cart> findCartById(Long cartId) throws ResourceNotFoundException {
        return Optional.ofNullable(cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("No Cart found with " +
                       "cart id : " +cartId)));
    }

    @Override
    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }
}
