package com.hzipyb.productservice.domain.product.repository;

import com.hzipyb.productservice.domain.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class ProductRepository {
    private final DynamoDbTable<Product> productDynamoDbTable;

    // READ : 상품 상세 조회
    public Optional<Product> getProductById(String productId) {

        String partitionPrefix = "PRODUCT#";
        String sortPrefix = "#CATEGORY#";

        String partitionKey = partitionPrefix + "PROD" + productId;

        Product product = productDynamoDbTable
                .query(r -> r.queryConditional(
                        QueryConditional.sortBeginsWith(k -> k.partitionValue(partitionKey)
                                .sortValue(sortPrefix))))
                .items()
                .stream()
                .findFirst()
                .orElse(null);

        return Optional.ofNullable(product);
    }

    // READ : 상품 카테고리별 리스트 조회
    // SK-PK-index GSI 사용
    public Optional<List<Product>> getProductsByCategoryId(String categoryId) {

        String partitionPrefix = "#CATEGORY#";
        String sortPrefix = "PRODUCT#";

        String partitionKey = partitionPrefix + categoryId;

        List<Product> products = productDynamoDbTable
                .index("SK-PK-index")
                .query(r -> r.queryConditional(
                        QueryConditional.sortBeginsWith(k ->
                                k.partitionValue(partitionKey)
                                        .sortValue(sortPrefix))))
                .stream()
                .flatMap(page -> page.items().stream())
                .collect(Collectors.toList());

        for(Product product : products){
            System.out.println("Product ID: " + product.getPK());
            System.out.println("Category ID: " + product.getSK());
            System.out.println("Entity Type: " + product.getEntityType());
            System.out.println("Name: " + product.getName());
            System.out.println("Description: " + product.getDescription());
            System.out.println("Price: " + product.getPrice());
            System.out.println("Created At: " + product.getCreatedAt());
            System.out.println("Updated At: " + product.getUpdatedAt());
            System.out.println("--------------------------------");
        }

        return Optional.of(products);
    }
}