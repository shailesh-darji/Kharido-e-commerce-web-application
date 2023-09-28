package com.kharido.controller;

import com.kharido.entity.Product;
import com.kharido.exception.ProductException;
import com.kharido.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

  @Autowired
  private ProductService productService;

  @GetMapping
  public ResponseEntity<Page<Product>> findProductByCategoryHandler(@RequestParam String category, @RequestParam List<String> color,
      @RequestParam List<String> size,@RequestParam Integer minPrice,@RequestParam Integer maxPrice,@RequestParam Integer minDiscount,
      @RequestParam String sort,@RequestParam String stock,@RequestParam Integer pageNumber,@RequestParam Integer pageSize)
  {
      Page<Product> res = productService.getAllproduct(category, color, size, minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize);
      return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
  }

  @GetMapping("/id/{productId}")
    public ResponseEntity<Product> findProductByIdHandler(@PathVariable Long productId) throws ProductException{
      Product product = productService.findProductById(productId);
      return new ResponseEntity<Product>(product,HttpStatus.ACCEPTED);
  }





}
