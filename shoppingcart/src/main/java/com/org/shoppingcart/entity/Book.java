package com.org.shoppingcart.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "book_table")
public class Book extends Product {

    private String genre;
    private String authour;
    private String publications;
}
