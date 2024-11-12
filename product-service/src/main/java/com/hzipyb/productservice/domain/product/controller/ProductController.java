package com.hzipyb.productservice.domain.product.controller;

import com.hzipyb.productservice.domain.product.dto.ProductDetailDTO;
import com.hzipyb.productservice.domain.product.exception.ProductNotFoundException;
import com.hzipyb.productservice.domain.product.service.ProductService;
import com.hzipyb.productservice.logging.CustomLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/product")
@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;
    private final CustomLogger customLogger;

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailDTO> getProductById(
            @PathVariable String productId){

        ProductDetailDTO productDetailDTO = productService.getProductById(productId);

        return ResponseEntity.ok(productDetailDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProductDetailDTO>> getProductsByCategoryId(
            @RequestParam(value = "categoryId") String categoryId,
            @RequestParam(value = "isSoldOutView", defaultValue = "false") boolean isSoldOutView){

        List<ProductDetailDTO> products = productService.getProductsByCategoryId(categoryId, isSoldOutView);

        return ResponseEntity.ok(products);
    }
}
