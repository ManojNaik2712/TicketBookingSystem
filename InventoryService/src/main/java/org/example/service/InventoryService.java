package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.Models.Event;
import org.example.Models.Venue;
import org.example.Repository.EventRepo;
import org.example.Repository.VenueRepo;
import org.example.Response.EventInventoryRespone;
import org.example.Response.VenueInventoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InventoryService {

    private EventRepo eventRepo;
    private VenueRepo venueRepo;

    @Autowired
    public InventoryService(final EventRepo eventRepo,
                            final VenueRepo venueRepo) {
        this.eventRepo = eventRepo;
        this.venueRepo=venueRepo;
    }

    public List<EventInventoryRespone> getAllevents() {
        final List<Event> events = eventRepo.findAll();
        return events.stream().map(event ->
                EventInventoryRespone.builder()
                        .event(event.getName())
                        .totalCapacity(event.getLeftCapacity())
                        .venue(event.getVenue())
                        .build()).collect(Collectors.toList());
    }

    public VenueInventoryResponse getVenueInformation(Long venueId) {
        Venue venue = venueRepo.findById(venueId).orElse(null);
        return VenueInventoryResponse.builder()
                .venueId(venue.getId())
                .venueName(venue.getName())
                .address(venue.getAddress())
                .totalCapacity(venue.getTotalCapacity()).build();
    }

    public EventInventoryRespone getEventInventory(Long eventId) {
        final Event event = eventRepo.findById(eventId).orElse(null);

        return EventInventoryRespone.builder()
                .event(event.getName())
                .venue(event.getVenue())
                .totalCapacity(event.getLeftCapacity())
                .ticketPrice(event.getTicketPrice())
                .eventId(event.getId())
                .build();
    }

    public void updateEventCapacity(Long eventId, Long ticketBooked) {
        final Event event = eventRepo.findById(eventId).orElse(null);
        event.setLeftCapacity(event.getLeftCapacity() - ticketBooked);
        eventRepo.saveAndFlush(event);
        log.info("updated event capacity for event id: {} with ticket booked: {}",eventId,ticketBooked);
    }
}

