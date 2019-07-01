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
@Table(name = "apparal_table")
public class Apparal extends Product {

    private String type;
    private String brand;
    private String design;
}
