package com.kharido.service;

import com.kharido.entity.Rating;
import com.kharido.entity.User;
import com.kharido.exception.ProductException;
import com.kharido.request.RatingRequest;

import java.util.List;

public interface RatingService {

    public Rating createRating(RatingRequest req, User user) throws ProductException;

    public List<Rating> getProductsRating(Long productId);
}
