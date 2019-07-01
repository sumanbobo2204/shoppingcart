package com.org.shoppingcart.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "user_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_unique_id")
    private Long userUniqueId;

    @NotNull
    @Size(max = 25)
    @Column(name = "user_id", unique = true)
    private String userName;

    @NotNull
    @Size(min=4, max = 12)
    @Column(name = "user_password")
    private String userPassword;

    @NotNull
    @Email
    @Size(max = 50)
    @Column(name = "email_id", unique = true)
    private String emailId;

    @NotNull
    @Size(min = 10, max = 10)
    @Column(name = "mobile_number")
    private String mobileNo;

    @Size(max = 100)
    @Column(name = "shipping_address", nullable = false)
    private String address;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_cart_id", referencedColumnName = "cart_id", nullable = false)
    private Cart cart;
}

