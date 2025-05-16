package org.example.Service;

import lombok.extern.slf4j.Slf4j;
import org.example.Client.InventoryServiceClient;
import org.example.DTO.BookingRequest;
import org.example.DTO.BookingResponse;
import org.example.DTO.InventoryResponse;
import org.example.Entity.Customer;
import org.example.Event.BookingEvent;
import org.example.Repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class BookingService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    InventoryServiceClient inventoryServiceClient;

    @Autowired
    KafkaTemplate<String,BookingEvent> kafkaTemplate;

    public BookingResponse createBooking(BookingRequest request) {
        //Checking if user exist or not
        final Customer customer=customerRepository.findById(request.getUserId()).orElse(null);
        if(customer == null){
            throw new RuntimeException("user not found");
        }

        //check if there is enough inventory
        final InventoryResponse inventoryResponse=inventoryServiceClient.getInventory(request.getEventId());
        log.info("Inventory Reponse: {}",inventoryResponse);
        if(inventoryResponse.getTotalCapacity() < request.getTicketCount()){
            throw new RuntimeException("Not enogh tickets");
        }

        //create booking
        final BookingEvent bookingEvent=createBookingEvent(request,customer,inventoryResponse);

        //send booking to Order service through kafka
        kafkaTemplate.send("booking",bookingEvent);
        log.info("Booking sent to kafka: {}", bookingEvent);

        return BookingResponse.builder()
                .userId(bookingEvent.getUserId())
                .eventId(bookingEvent.getEventId())
                .ticketCount(bookingEvent.getTicketCount())
                .totalPrice(bookingEvent.getTicketPrice())
                .build();
    }

    //creates a Bookingevent object
    private BookingEvent createBookingEvent(BookingRequest request, Customer customer, InventoryResponse inventoryResponse) {
        return BookingEvent.builder()
                .userId(customer.getId())
                .eventId(request.getEventId())
                .ticketCount(request.getTicketCount())
                .ticketPrice(inventoryResponse.getTicketPrice().multiply(BigDecimal.valueOf(request.getTicketCount())))
                .build();
    }
}
