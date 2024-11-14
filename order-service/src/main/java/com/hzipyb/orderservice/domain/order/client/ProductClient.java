package com.hzipyb.orderservice.domain.order.client;

import com.hzipyb.orderservice.domain.order.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class ProductClient {

    private final RestTemplate restTemplate;
    private final String productServiceUrl;

    public ProductClient(RestTemplate restTemplate,
                         @Value("${product.service.url}") String productServiceUrl){
        this.restTemplate = restTemplate;
        this.productServiceUrl = productServiceUrl;
    }

    public ProductDTO getProductByProductId(Long productId){
        String url = productServiceUrl + "/" + productId;

        return restTemplate.getForObject(url, ProductDTO.class);
    }
}
