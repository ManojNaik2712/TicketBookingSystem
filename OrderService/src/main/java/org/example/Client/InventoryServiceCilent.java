package org.example.Client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InventoryServiceCilent {
    @Value("${inventory.service.url}")
    private String url;

    public ResponseEntity<Void> updateInventory(final Long eventId,
                                                final Long ticketCount){
        RestTemplate restTemplate=new RestTemplate();
        restTemplate.put(url + eventId + "/capacity/" + ticketCount,null);
        return ResponseEntity.ok().build();
    }
}
