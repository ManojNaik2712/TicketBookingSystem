package org.example.Client;

import org.example.DTO.InventoryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InventoryServiceClient {

    @Value("${inventory.service.url}")
    private String inventoryServiceurl;

    public InventoryResponse getInventory(final Long eventId) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(inventoryServiceurl + eventId, InventoryResponse.class);
    }
}
