package com.org.shoppingcart.controller;

import com.org.shoppingcart.constants.ShoppingCartConstants;
import com.org.shoppingcart.dto.ProductCategory;
import com.org.shoppingcart.exception.ResourceNotFoundException;
import com.org.shoppingcart.service.CartService;
import com.org.shoppingcart.dto.ProductDTO;
import com.org.shoppingcart.entity.Cart;
import com.org.shoppingcart.entity.Product;
import com.org.shoppingcart.service.UserService;
import com.org.shoppingcart.util.DtoConverter;
import com.org.shoppingcart.util.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
@Log4j2
@RequiredArgsConstructor
@Api(value="Shopping cart System", description="Operations pertaining to cart in shopping cart System")
public class CartController {

    final UserService userService;

    final CartService cartService;

    final Validator validator;

    @ApiOperation(value = "Adding a product in a specific cart ", response = String.class)
    @PostMapping("/{cartId}/addProduct")
    public ResponseEntity<String> addProductToCart(@RequestBody final ProductDTO productDTO,
                                           @PathVariable  final String cartId) throws ResourceNotFoundException {
        log.info("creating product -> " +productDTO);
        validator.validateProduct(productDTO);
        Cart cart = cartService.findCartById(Long.valueOf(cartId)).orElseThrow(() ->
                new ResourceNotFoundException(ShoppingCartConstants.INVALID_CART_MSG));
        Optional<Product> product = cartService.getProductDetailsByProductCode(Long.valueOf(cartId), productDTO.getProductCode());
        //existing products -> update quantity::
        if(product.isPresent()) {
            cartService.updateProductByIdInCart(Long.valueOf(cartId), productDTO.getProductCode(),
                    productDTO.getProductQuantity()+1);
        }
        // new product ::
        else {
            if(cart.getProductsList() == null || cart.getProductsList().isEmpty()) {
                List<Product> products = new ArrayList<>();
                Product productToAdd = DtoConverter.convertProductDtoToEntity(productDTO);
                products.add(productToAdd);
                cart.setProductsList(products);
                productToAdd.setCart(cart);
                cartService.saveCart(cart);
            }
           else{
               List<Product> productsInCart = cart.getProductsList();
               Product productToAdd = DtoConverter.convertProductDtoToEntity(productDTO);
               productsInCart.add(productToAdd);
               cart.setProductsList(productsInCart);
               productToAdd.setCart(cart);
               cartService.saveCart(cart);
            }
        }
        return new ResponseEntity(ShoppingCartConstants.PRODUCT_ADD_MSG , HttpStatus.CREATED);
    }


    @ApiOperation(value = "Get all the product details from a specific cart", response = Cart.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved resource"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/{cartId}/getAllProducts")
    public ResponseEntity<Cart> getAllProductDetailsFromCart(@PathVariable  final String cartId) throws ResourceNotFoundException {

        Cart cart = cartService.findCartById(Long.valueOf(cartId)).orElseThrow(() ->
                new ResourceNotFoundException(ShoppingCartConstants.INVALID_CART_MSG));
        List<Product> products = cart.getProductsList();
        //Assert.notNull(products,"Product Details not exists in cart");
        //Assert.notEmpty(products,"Product Details not exists in cart");
        Double totalPrice = products.stream()
                            .mapToDouble(p -> p.getProdPrice()*p.getProductQuantity())
                            .sum();
        cart.setTotalPrice(totalPrice);

        return new ResponseEntity(cart,HttpStatus.OK);
    }


    @ApiOperation(value = "Get a specific product w.r.t productCode from a specific cart", response = Product.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved resource"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/getProductByCode")
    public ResponseEntity<Product> getProductByProductCodeFromCart(@RequestParam final String cartId,
                                                        @RequestParam("productCode") final String productCode) throws ResourceNotFoundException {
        Cart cart = cartService.findCartById(Long.valueOf(cartId)).orElseThrow(() ->
                new ResourceNotFoundException(ShoppingCartConstants.INVALID_CART_MSG));
        Product product = cartService.getProductDetailsByProductCode(Long.valueOf(cartId), productCode)
                            .orElseThrow(() -> new ResourceNotFoundException("No product found " +
                                    "for productCode " +productCode+ " and cartId " +cartId));

        return new ResponseEntity(product,HttpStatus.OK);
    }


    @ApiOperation(value = "Get a specific product w.r.t productName from a specific cart", response = Product.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved resource"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/getProductByName")
    public ResponseEntity<Product> getProductByProductNameFromCart(@RequestParam final String cartId,
                                                           @RequestParam("productName") final String productName) throws ResourceNotFoundException {
        Cart cart = cartService.findCartById(Long.valueOf(cartId)).orElseThrow(() ->
                new ResourceNotFoundException(ShoppingCartConstants.INVALID_CART_MSG));
        Optional<Product> product = cartService.getProductDetailsByProductName(Long.valueOf(cartId), productName);

        return new ResponseEntity(product.get(),HttpStatus.OK);
    }


    @ApiOperation(value = "Get products w.r.t productCategory from a specific cart", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/{cartId}/{productCategory}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable final String cartId,
                                                      @PathVariable final String productCategory) throws ResourceNotFoundException {
        Cart cart = cartService.findCartById(Long.valueOf(cartId)).orElseThrow(() ->
                new ResourceNotFoundException(ShoppingCartConstants.INVALID_CART_MSG));
        List<Product> products = null;
        Assert.isTrue(productCategory.equals(ProductCategory.BOOK.name())||
                        productCategory.equals(ProductCategory.APPARAL.name()),
                        ShoppingCartConstants.WRONG_PRODUCT_CATEGORY_MSG);

        if(productCategory.trim().equalsIgnoreCase(ProductCategory.BOOK.name())) {
            products = cartService.getBooksByProductCategory(Long.valueOf(cartId));
        }
        if(productCategory.trim().equalsIgnoreCase(ProductCategory.APPARAL.name())) {
            products =  cartService.getApparalsByProductCategory(Long.valueOf(cartId));
        }
        return new ResponseEntity<>(products,HttpStatus.OK);
    }


    @ApiOperation(value = "Update a product in a specific cart", response = String.class)
    @PutMapping("/{cartId}")
    public ResponseEntity<String> updateProductInCart(@PathVariable final String cartId,
                                                      @RequestParam final String productCode,
                                                      @RequestParam final String productQuantity) throws ResourceNotFoundException {

        Product product = cartService.getProductDetailsByProductCode(Long.valueOf(cartId), productCode)
                .orElseThrow(() -> new ResourceNotFoundException("No product found with code " +
                        " " +productCode+ " and cart id : " +cartId));
        if(product != null && Integer.valueOf(productQuantity) > 0) {
            cartService.updateProductByIdInCart(Long.valueOf(cartId), productCode,
                    Integer.valueOf(productQuantity));
        }
        else if(Integer.valueOf(productQuantity) == 0 || Integer.valueOf(productQuantity) < 0) {
            cartService.deleteProductByIdFromCart(Long.valueOf(cartId), productCode);
        }
        return new ResponseEntity<>("Product " +productCode+ " updated in cartId " +cartId, HttpStatus.OK);
    }



    @ApiOperation(value = "Remove a product in a specific cart", response = String.class)
    @DeleteMapping("/{cartId}/{productCode}")
    public ResponseEntity<String> removeProductByIdFromCart(@PathVariable final String cartId,
                                                            @PathVariable final String productCode) throws ResourceNotFoundException {
        Product product = cartService.getProductDetailsByProductCode(Long.valueOf(cartId), productCode)
                            .orElseThrow(() -> new ResourceNotFoundException("No product found with code " +
                                    " " +productCode+ " and cart id : " +cartId));
        cartService.deleteProductByIdFromCart(Long.valueOf(cartId), productCode);

        return new ResponseEntity<>(ShoppingCartConstants.PRODUCT_DELETED_MSG +cartId, HttpStatus.ACCEPTED);
    }


    @ApiOperation(value = "Remove all the products in a specific cart", response = String.class)
    @DeleteMapping("/{cartId}/deleteAllProducts")
    public ResponseEntity<String> removeAllProductsFromCart(@PathVariable final String cartId) throws ResourceNotFoundException {

        Cart cart = cartService.findCartById(Long.valueOf(cartId)).orElseThrow(() ->
                new ResourceNotFoundException(ShoppingCartConstants.INVALID_CART_MSG));
        cartService.deleteAllProductsFromCart(Long.valueOf(cartId));
        return new ResponseEntity<>(ShoppingCartConstants.ALL_PRODUCT_DELETED_MSG +cartId, HttpStatus.ACCEPTED);

    }


}
