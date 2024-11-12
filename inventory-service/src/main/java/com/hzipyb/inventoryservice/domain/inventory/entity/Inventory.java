package com.hzipyb.inventoryservice.domain.inventory.entity;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@Builder
@DynamoDbBean
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Inventory {

    private String PK; // inventoryId
    private String SK; // productId

    private String entityType;
    private String name;
    private Integer stockQuantity;
    private Integer changeQuantity;
    private String currentEvent;
    private String updatedAt;

    // Partition Key (inventoryId)
    @DynamoDbPartitionKey
    @DynamoDbAttribute("PK")
    public String getPK() {
        return PK;
    }

    // Sort Key (productId)
    @DynamoDbSortKey
    @DynamoDbAttribute("SK")
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

    @DynamoDbAttribute("StockQuantity")
    public Integer getStockQuantity() {
        return stockQuantity;
    }

    @DynamoDbAttribute("ChangeQuantity")
    public Integer getChangeQuantity() {
        return changeQuantity;
    }

    @DynamoDbAttribute("CurrentEvent")
    public String getCurrentEvent() {
        return currentEvent;
    }

    @DynamoDbAttribute("UpdatedAt")
    public String getUpdatedAt() {
        return updatedAt;
    }
}
