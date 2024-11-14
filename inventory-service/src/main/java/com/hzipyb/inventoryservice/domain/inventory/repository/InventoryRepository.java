package com.hzipyb.inventoryservice.domain.inventory.repository;

import com.hzipyb.inventoryservice.domain.inventory.entity.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class InventoryRepository{
    private final DynamoDbTable<Inventory> inventoryDynamoDbTable;

    // READ : 재고 상세 조회
    public Optional<Inventory> getInventoryById(Long productId){

        String partitionPrefix = "INVENTORY#";
        String sortPrefix = "PRODUCT#";

        String partitionKey = partitionPrefix + productId.toString();
        String sortKey = sortPrefix + productId.toString();

        Inventory inventory = inventoryDynamoDbTable.getItem(r -> r.key(k ->
                k.partitionValue(partitionKey)
                 .sortValue(sortKey)));

        return Optional.ofNullable(inventory);
    }

    // Update : 재고 변동 업데이트
    public Optional<Inventory> updateInventory(Inventory inventory){

        inventoryDynamoDbTable.putItem(inventory);

        Inventory resultInventory = inventoryDynamoDbTable.getItem(r -> r.key(k ->
                k.partitionValue(inventory.getPK())
                        .sortValue(inventory.getSK())));

        return Optional.ofNullable(resultInventory);
    }
}
