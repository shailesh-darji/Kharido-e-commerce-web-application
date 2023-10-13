package com.kharido.controller;

import com.kharido.entity.Review;
import com.kharido.entity.User;
import com.kharido.exception.ProductException;
import com.kharido.exception.UserException;
import com.kharido.request.ReviewRequest;
import com.kharido.service.ReviewService;
import com.kharido.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest req,
        @RequestHeader("Authorization") String jwt) throws UserException, ProductException{

        User user = userService.findUserByToken(jwt);

        Review review = reviewService.createReview(req,user);

        return  new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getProductsReview(@PathVariable Long productId) throws UserException,ProductException{

        List<Review> reviews = reviewService.getAllReview(productId);

        return new ResponseEntity<>(reviews,HttpStatus.CREATED);
    }

}
