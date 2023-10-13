package com.kharido.controller;

import com.kharido.entity.Rating;
import com.kharido.entity.User;
import com.kharido.exception.ProductException;
import com.kharido.exception.UserException;
import com.kharido.request.RatingRequest;
import com.kharido.service.RatingService;
import com.kharido.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private UserService userService;

    @Autowired
    private RatingService ratingService;

    @PostMapping("/create")
    public ResponseEntity<Rating> createRating(@RequestBody RatingRequest req, @RequestHeader("Authorization") String jwt)
        throws UserException, ProductException{
        User user = userService.findUserByToken(jwt);

        Rating rating = ratingService.createRating(req,user);

        return new ResponseEntity<>(rating, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Rating>> getProductsRating(@PathVariable Long productId,
        @RequestHeader("Authorization") String jwt) throws UserException,ProductException{

        User user = userService.findUserByToken(jwt);
        List<Rating> ratings = ratingService.getProductsRating(productId);

        return new ResponseEntity<>(ratings,HttpStatus.CREATED);
    }
}
