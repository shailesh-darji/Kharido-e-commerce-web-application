package com.kharido.service;

import com.kharido.entity.Address;
import com.kharido.entity.Order;
import com.kharido.entity.User;
import com.kharido.exception.OrderException;

import java.util.List;

public interface OrderService {
    public Order createOrder(User user, Address shippAddress);

    public Order findOrderById(Long orderId) throws OrderException;

    public List<Order> usersOrderHistory(Long userId);

    public Order placedOrder(Long orderId) throws OrderException;

    public Order confirmedOrder(Long orderId) throws OrderException;

    public Order shippedOrder(Long orderId) throws OrderException;

    public Order deliveredOrder(Long orderId) throws OrderException;

    public Order cancelOrder(Long orderId) throws OrderException;

    public List<Order> getAllOrders();

    public void deleteOrder(Long orderId) throws OrderException;
}
