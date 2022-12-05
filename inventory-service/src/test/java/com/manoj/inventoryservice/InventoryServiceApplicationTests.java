package com.manoj.inventoryservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manoj.inventoryservice.dto.InventoryRequest;
import com.manoj.inventoryservice.model.Inventory;
import com.manoj.inventoryservice.repository.InventoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class InventoryServiceApplicationTests {
    @Container
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:latest");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private InventoryRepository inventoryRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", mySQLContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

//	@Test
//	void shouldCheckInventory() throws Exception {
//        List<InventoryRequest> inventoryRequest = getInventoryRequest();
//        List<String> inventoryRequestObjectMapper = Collections.singletonList(objectMapper.writeValueAsString(inventoryRequest));
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/inventory/{sku-code}", getInventoryRequest().get(0).getSkuCode())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(inventoryRequestObjectMapper.toString()))
//                .andExpect(status().isOk());
//
//	}

    @Test
    void shouldReturnDataFromInventoryRepository() {
        List<Inventory> inventory = inventoryRepository.findAll();
        assertEquals(3, inventory.size());
    }

    List<InventoryRequest> getInventoryRequest() {
        return List.of(
                InventoryRequest.builder()
                        .skuCode("sku-code-1")
                        .quantity(10)
                        .build(),
                InventoryRequest.builder()
                        .skuCode("sku-code-2")
                        .quantity(20)
                        .build()
        );
    }

}
