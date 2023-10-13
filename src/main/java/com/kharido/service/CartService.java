package com.kharido.service;

import com.kharido.entity.Cart;
import com.kharido.entity.User;
import com.kharido.exception.ProductException;
import com.kharido.request.AddItemRequest;

public interface CartService {

    public Cart createCart(User user);

    public String addCartItem(Long userId, AddItemRequest req) throws ProductException;

    public Cart findUserCart(Long userId);
}
