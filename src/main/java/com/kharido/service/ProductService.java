package com.kharido.service;

import com.kharido.entity.Product;
import com.kharido.exception.ProductException;
import com.kharido.request.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    public Product createProduct(CreateProductRequest request);

    public String deleteProduct(Long productId) throws ProductException;

    public Product updateProduct(Long id,Product req) throws ProductException;

    public Product findProductById(Long id) throws ProductException;

    public List<Product> findProductByCategory(String category);

    public Page<Product> getAllproduct(String category,List<String> colors,List<String> sizes,Integer minPrice,Integer maxPrice,
                                       Integer minDiscount,String sort,String stock,Integer pageNumber,Integer pageSize);

    public List<Product> findAllProducts();
}
