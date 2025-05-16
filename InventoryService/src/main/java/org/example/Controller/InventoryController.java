package org.example.Controller;


import org.example.Response.EventInventoryRespone;
import org.example.Response.VenueInventoryResponse;
import org.example.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class InventoryController {

    @Autowired
    InventoryService inventoryService;

    @GetMapping("/get/events")
    public @ResponseBody List<EventInventoryRespone> inventoryGetAllEvents(){
        return inventoryService.getAllevents();
    }

    @GetMapping("/get/venue/{venueId}")
    public VenueInventoryResponse inventoryByVenueId(@PathVariable("venueId") Long venueId){
        return inventoryService.getVenueInformation(venueId);
    }
    @GetMapping("/get/event/{eventId}")
    public @ResponseBody EventInventoryRespone inventoryForEvent(@PathVariable("eventId") Long eventId){
        return inventoryService.getEventInventory(eventId);
    }
    @PutMapping("/update/event/{eventId}/capacity/{capacity}")
    public ResponseEntity<Void> updateEventCapacity(@PathVariable("eventId") Long eventId,
                                                    @PathVariable("capacity") Long capacity){
        inventoryService.updateEventCapacity(eventId,capacity);
        return ResponseEntity.ok().build();
    }

}
