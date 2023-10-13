package com.kharido.service;

import com.kharido.entity.Cart;
import com.kharido.entity.CartItem;
import com.kharido.entity.Product;
import com.kharido.exception.CartItemException;
import com.kharido.exception.UserException;

import java.util.List;

public interface CartItemService {

    public CartItem createCartItem(CartItem cartItem);

    public CartItem updateCartItem(Long userId,Long id,CartItem cartItem) throws CartItemException, UserException;

    public CartItem isCartItemExist(Cart cart, Product product,String size,Long userId);

    public void removeCartItem(Long userId,Long cartItemId) throws CartItemException,UserException;

    public CartItem findCartItemById(Long cartItemId) throws CartItemException;

    public List<CartItem> findCartItemByUserId(Long userId);
}
