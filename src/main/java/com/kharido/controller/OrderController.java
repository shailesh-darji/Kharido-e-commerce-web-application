package com.kharido.controller;

import com.kharido.entity.Address;
import com.kharido.entity.Order;
import com.kharido.entity.User;
import com.kharido.exception.OrderException;
import com.kharido.exception.UserException;
import com.kharido.service.OrderService;
import com.kharido.service.UserService;
import com.kharido.service.impl.JwtServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;


    @PostMapping("/")
    public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress,
                                             @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserByToken(jwt);
        System.out.println("Order Create");
        Order order = orderService.createOrder(user, shippingAddress);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> usersOrdersHistory(@RequestHeader("Authorization") String jwt
    ) throws UserException {
        User user = userService.findUserByToken(jwt);
        List<Order> orders = orderService.usersOrderHistory(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findOrderById(@PathVariable Long id,@RequestHeader("Authorization") String jwt) throws
            UserException, OrderException {
        User user = userService.findUserByToken(jwt);

        Order order = orderService.findOrderById(id);

        return new ResponseEntity<>(order,HttpStatus.OK);
    }
}
