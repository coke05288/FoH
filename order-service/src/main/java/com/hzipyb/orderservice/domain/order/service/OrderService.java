package com.hzipyb.orderservice.domain.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hzipyb.orderservice.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

//    public void orderProductById(Long id) throws JsonProcessingException {
//
//        // 1. 상품(Product) 조회
//        Product product = this.productRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//
//        // 2. 조회 결과 json 타입 변환 및 SQS에 메세지 전송
//        ObjectMapper jsonMapper = new ObjectMapper();
//        String message = jsonMapper.writeValueAsString(product);
//
//        sqsMessageSender.sendMessage(message);
//
//        // 3. 재고 서비스(inventory-service)에 reduce/{id} api 요청
//        try{
//            ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, null, String.class);
//            return response.getStatusCode().is2xxSuccessful();
//        }catch (RestClientException e){
//            e.printStackTrace();
//            return false;
//        }
//    }
}
