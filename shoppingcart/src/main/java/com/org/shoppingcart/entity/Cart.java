package com.org.shoppingcart.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "cart_table")
@NamedQueries(value = {
        @NamedQuery(name = "Cart.getProductDetailsByProductCode", query = "SELECT p FROM Product p JOIN Cart c on p.cart.cartId=" +
                "c.cartId where p.cart.cartId=?1 and p.productCode=?2"),
        @NamedQuery(name = "Cart.getProductDetailsByProductName", query = "SELECT p FROM Product p JOIN Cart c on p.cart.cartId=" +
                "c.cartId where p.cart.cartId=?1 and p.prodName=?2"),
        @NamedQuery(name = "Cart.getBooksByProductCategory", query = "SELECT p FROM Book p JOIN Cart c on p.cart.cartId=" +
                "c.cartId where p.cart.cartId=?1"),
        @NamedQuery(name = "Cart.getApparalsByProductCategory", query = "SELECT p FROM Apparal p JOIN Cart c on p.cart.cartId=" +
                "c.cartId where p.cart.cartId=?1"),
        @NamedQuery(name = "Cart.deleteAllProductsFromCart", query = "DELETE FROM Product p " +
                "WHERE p.cart.cartId=?1"),
        @NamedQuery(name = "Cart.deleteProductByIdFromCart", query = "DELETE FROM Product p " +
                "WHERE p.cart.cartId=?1 AND p.productCode=?2"),
        @NamedQuery(name = "Cart.updateProductByIdInCart", query = "UPDATE Product p SET p.productQuantity=?3" +
                "WHERE p.cart.cartId=?1 AND p.productCode=?2")
})
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cart_id")
    private Long cartId;

    @Column(nullable = true)
    private String cartName;

    @Transient
    private Double totalPrice;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "cart", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Product> productsList = new ArrayList<>();

}
