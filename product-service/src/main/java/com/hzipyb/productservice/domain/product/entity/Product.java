package com.hzipyb.productservice.domain.product.entity;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

@Builder
@DynamoDbBean
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Product {

    private String PK;      // productId
    private String SK;      // categoryId

    private String entityType;

    private String name;
    private String description;
    private Integer price;
    private String createdAt;
    private String updatedAt;

    // Partition Key (productId)
    @DynamoDbPartitionKey
    @DynamoDbAttribute("PK")
    @DynamoDbSecondarySortKey(indexNames = "SK-PK-index")
    public String getPK() {
        return PK;
    }

    // Sort Key (categoryId)
    @DynamoDbSortKey
    @DynamoDbAttribute("SK")
    @DynamoDbSecondaryPartitionKey(indexNames = "SK-PK-index")
    public String getSK() {
        return SK;
    }

    @DynamoDbAttribute("EntityType")
    public String getEntityType() {
        return entityType;
    }

    @DynamoDbAttribute("Name")
    public String getName() {
        return name;
    }

    @DynamoDbAttribute("Description")
    public String getDescription() {
        return description;
    }

    @DynamoDbAttribute("Price")
    public Integer getPrice() {
        return price;
    }

    @DynamoDbAttribute("CreatedAt")
    public String getCreatedAt() {
        return createdAt;
    }

    @DynamoDbAttribute("UpdatedAt")
    public String getUpdatedAt() {
        return updatedAt;
    }
}
