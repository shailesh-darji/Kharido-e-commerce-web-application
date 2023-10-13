package com.kharido.request;

import com.kharido.entity.Product;
import lombok.Data;

@Data
public class RatingRequest {
    private Long productId;
    private double rating;
}
