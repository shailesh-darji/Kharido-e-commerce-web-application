package com.kharido.service.impl;

import com.kharido.entity.Product;
import com.kharido.entity.Review;
import com.kharido.entity.User;
import com.kharido.exception.ProductException;
import com.kharido.repository.ReviewRepository;
import com.kharido.request.ReviewRequest;
import com.kharido.service.ProductService;
import com.kharido.service.ReviewService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    final private ReviewRepository reviewRepository;
    final private ProductService productService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ProductService productService) {
        this.reviewRepository = reviewRepository;
        this.productService = productService;
    }

    @Override
    public Review createReview(ReviewRequest req, User user) throws ProductException {
        Product product = productService.findProductById(req.getProductId());

        Review review = new Review();
        review.setProduct(product);
        review.setUser(user);
        review.setReview(req.getReview());
        review.setCreatedAt(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReview(Long productId) {
        return reviewRepository.getAllProductsReview(productId);
    }
}
