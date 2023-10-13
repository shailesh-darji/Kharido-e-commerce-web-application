package com.kharido.controller;

import com.kharido.entity.CartItem;
import com.kharido.entity.User;
import com.kharido.exception.CartItemException;
import com.kharido.exception.UserException;
import com.kharido.response.ApiResponse;
import com.kharido.service.CartItemService;
import com.kharido.service.UserService;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/cart_items")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @DeleteMapping("/{cartItemId}")
    @Operation(description = "Remove Cart Item From Cart")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Delete Item")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Long cartItemId,
        @RequestHeader("Authorization") String jwt) throws UserException, CartItemException{

        User user = userService.findUserByToken(jwt);
        cartItemService.removeCartItem(user.getId(),cartItemId);

        ApiResponse res = new ApiResponse();
        res.setMessage("item delete from cart");
        res.setStatus(true);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItem(@PathVariable Long cartItemId,@RequestBody CartItem cartItem,
        @RequestHeader("Authorization") String jwt) throws UserException,CartItemException{
        User user = userService.findUserByToken(jwt);

        CartItem res = cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);

        return new ResponseEntity<>(res,HttpStatus.OK);
    }
}
