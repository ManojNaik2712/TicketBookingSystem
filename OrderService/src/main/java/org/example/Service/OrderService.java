package org.example.Service;

import lombok.extern.slf4j.Slf4j;
import org.example.DTO.BookingEvent;
import org.example.Entity.Order;
import org.example.Repo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @KafkaListener(topics = "booking",groupId = "order-service")
    public void orderEvent(BookingEvent bookingEvent){
        log.info("Recieved order event: {}",bookingEvent);

        //Create a order object for DB
        Order order=createOrder(bookingEvent);
        orderRepository.saveAndFlush(order);

    }

    private Order createOrder(BookingEvent bookingEvent) {
        return Order.builder()
                .customerId(bookingEvent.getUserId())
                .eventId(bookingEvent.getEventId())
                .ticketCount(bookingEvent.getTicketCount())
                .totalPrice(bookingEvent.getTotalPrice())
                .build();
    }
}
