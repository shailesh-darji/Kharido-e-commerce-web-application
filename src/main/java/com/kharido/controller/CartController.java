package com.kharido.controller;

import com.kharido.entity.Cart;
import com.kharido.entity.User;
import com.kharido.exception.ProductException;
import com.kharido.exception.UserException;
import com.kharido.request.AddItemRequest;
import com.kharido.response.ApiResponse;
import com.kharido.service.CartService;
import com.kharido.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "Cart Management System",description = "find user cart,add item to cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    @Operation(description = "find cart by user id")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException{
        User user = userService.findUserByToken(jwt);
        Cart cart = cartService.findUserCart(user.getId());

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/add")
    @Operation(description = "add item to cart")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest req,
        @RequestHeader("Authorization") String jwt) throws UserException, ProductException{
        User user = userService.findUserByToken(jwt);
        System.out.println(req);
        cartService.addCartItem(user.getId(),req);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("item added to cart");
        apiResponse.setStatus(true);

        return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
    }
}
