package com.org.shoppingcart.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "product_id", updatable = false, nullable = false)
    private Long productId;

    @Column(name = "product_code", nullable = false)
    private String productCode;

    @NotNull
    @Size(max = 100)
    @Column(name = "product_name")
    private String prodName;

    @NotNull
    @Column(name = "product_price")
    private Double prodPrice;

    @NotNull
    @Column(name = "product_quantity")
    private Integer productQuantity;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "cart_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Cart cart;
}
