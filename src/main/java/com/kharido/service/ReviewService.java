package com.kharido.service;

import com.kharido.entity.Review;
import com.kharido.entity.User;
import com.kharido.exception.ProductException;
import com.kharido.request.ReviewRequest;

import java.util.List;

public interface ReviewService {

    public Review createReview(ReviewRequest req, User user) throws ProductException;

    public List<Review> getAllReview(Long productId);
}
