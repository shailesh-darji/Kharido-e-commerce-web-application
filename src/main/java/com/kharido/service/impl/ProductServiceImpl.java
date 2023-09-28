package com.kharido.service.impl;

import com.kharido.entity.Category;
import com.kharido.entity.Product;
import com.kharido.exception.ProductException;
import com.kharido.repository.CategoryRepository;
import com.kharido.repository.ProductRepository;
import com.kharido.request.CreateProductRequest;
import com.kharido.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public Product createProduct(CreateProductRequest request) {
        Category topLevel = categoryRepository.findByName(request.getTopLevelCategory());
        if (topLevel == null) {
            Category topLevelCategory = new Category();
            topLevelCategory.setName(request.getTopLevelCategory());
            topLevelCategory.setLevel(1);

            topLevel = categoryRepository.save(topLevelCategory);
        }

        Category secondLevel = categoryRepository.findByNameAndParent(request.getSecondLevelCategory(), topLevel.getName());
        if (secondLevel == null) {
            Category secondLevelCategory = new Category();
            secondLevelCategory.setName(request.getSecondLevelCategory());
            secondLevelCategory.setParentCategory(topLevel);
            secondLevelCategory.setLevel(2);

            secondLevel = categoryRepository.save(secondLevelCategory);
        }

        Category thirdLevel = categoryRepository.findByNameAndParent(request.getThirdLevelCategory(), secondLevel.getName());

        if (thirdLevel == null) {
            Category thirdLevelCategory = new Category();
            thirdLevelCategory.setName(request.getThirdLevelCategory());
            thirdLevelCategory.setParentCategory(secondLevel);
            thirdLevelCategory.setLevel(3);

            thirdLevel = categoryRepository.save(thirdLevelCategory);
        }

        Product product = Product.builder().title(request.getTitle())
                .color(request.getColor())
                .description(request.getDescription())
                .discountedPrice(request.getDiscountedPrice())
                .discountPercentage(request.getDiscountPercentage())
                .imageUrl(request.getImageUrl())
                .brand(request.getBrand())
                .price(request.getPrice())
                .sizes(request.getSize())
                .quantity(request.getQuantity())
                .category(thirdLevel)
                .createdAt(LocalDateTime.now()).build();
        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        Product product = findProductById(productId);
        product.getSizes().clear();
        productRepository.delete(product);
        return "Product Deleted Successfully";
    }

    @Override
    public Product updateProduct(Long id, Product req) throws ProductException {
        Product product = findProductById(id);

        if (req.getQuantity() != 0) {
            product.setQuantity(req.getQuantity());
        }
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long id) throws ProductException {
        Optional<Product> opt = productRepository.findById(id);

        if (opt.isPresent()) {
            return opt.get();
        }
        throw new ProductException("Product not found with id-" + id);
    }

    @Override
    public List<Product> findProductByCategory(String category) {
        return null;
    }

    @Override
    public Page<Product> getAllproduct(String category, List<String> colors, List<String> sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<Product> products = productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);

        if (!colors.isEmpty()) {
            products = products.stream()
                    .filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
                    .collect(Collectors.toList());
        }

        if(stock != null){
            if(stock.equals("in_stock")){
                products = products.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList());
            }
            else if(stock.equals("out_of_stock")){
                products = products.stream().filter(p -> p.getQuantity()<1).collect(Collectors.toList());
            }
        }

        int startIndex = (int)pageable.getOffset();
        int endIndex = Math.min(startIndex+pageable.getPageSize(),products.size());
        List<Product> pageContent = products.subList(startIndex,endIndex);
        Page<Product> filteredProducts = new PageImpl<>(pageContent,pageable,products.size());
        return filteredProducts;
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
}
