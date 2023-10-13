package com.kharido.service.impl;

import com.kharido.entity.Cart;
import com.kharido.entity.CartItem;
import com.kharido.entity.Product;
import com.kharido.entity.User;
import com.kharido.exception.CartItemException;
import com.kharido.exception.UserException;
import com.kharido.repository.CartItemRepository;
import com.kharido.repository.CartRepository;
import com.kharido.service.CartItemService;
import com.kharido.service.CartService;
import com.kharido.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {

    final private CartItemRepository cartItemRepository;
    final private UserService userService;
    final private CartRepository cartRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, UserService userService, CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.userService = userService;
        this.cartRepository = cartRepository;
    }

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getProduct().getPrice()+cartItem.getPrice());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());

        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
        CartItem item = findCartItemById(id);
        User user = userService.findUserById(item.getUserId());

        if(user.getId().equals(userId))
        {
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getQuantity()*item.getProduct().getPrice());
            item.setDiscountedPrice(item.getProduct().getDiscountedPrice()* item.getQuantity());
        }
        return cartItemRepository.save(item);
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
        CartItem cartItem = cartItemRepository.isCartItemExist(cart, product, size, userId);
        return cartItem;
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
       CartItem cartItem = findCartItemById(cartItemId);

       User user = userService.findUserById(cartItem.getUserId());
       User reqUser =userService.findUserById(userId);

       if(user.getId().equals(reqUser.getId())){
           cartItemRepository.deleteById(cartItemId);
       }
       else{
           throw new UserException("you can't remove another users item");
       }

    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItem> opt = cartItemRepository.findById(cartItemId);

        if(opt.isPresent()){
          return opt.get();
        }

        throw  new CartItemException("cartItem not found with id : "+cartItemId);
    }

    @Override
    public List<CartItem> findCartItemByUserId(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }
}
