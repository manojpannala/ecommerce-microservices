package com.manoj.inventoryservice.service;

import com.manoj.inventoryservice.dto.InventoryResponse;
import com.manoj.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        return inventoryRepository.findBySkuCodeIn(skuCode)
                .stream()
                .map(e -> InventoryResponse.builder()
                        .skuCode(e.getSkuCode())
                        .isInStock(e.getQuantity() > 0)
                        .build())
                .collect(Collectors.toList());
    }
}
