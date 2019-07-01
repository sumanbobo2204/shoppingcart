package com.org.shoppingcart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDTO {

    //private Long productUniqueId;
    private String productCode;
    private String prodName;
    private Double prodPrice;
    private Integer productQuantity;

    private String bookGenre;
    private String bookAuthour;
    private String bookPublications;

    private String apparalType;
    private String apparalBrand;
    private String apparalDesign;

    //For Product Category like Book, Apparal
    private String productType;
}
