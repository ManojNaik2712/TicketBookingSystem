package org.example.Service;

import lombok.extern.slf4j.Slf4j;
import org.example.Client.InventoryServiceCilent;
import org.example.Entity.Order;
import org.example.Event.BookingEvent;
import org.example.Repo.OrderRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryServiceCilent inventoryServiceCilent;

    public OrderService(final OrderRepository orderRepository, final InventoryServiceCilent inventoryServiceCilent) {
        this.orderRepository = orderRepository;
        this.inventoryServiceCilent = inventoryServiceCilent;
    }

    @KafkaListener(topics = "booking", groupId = "order-service")
    public void orderEvent(BookingEvent bookingEvent) {
        log.info("Recieved order event: {}", bookingEvent);

        //Create a order object for DB
        Order order = createOrder(bookingEvent);
        orderRepository.saveAndFlush(order);

        //Update the inventory
        inventoryServiceCilent.updateInventory(order.getEventId(), order.getTicketCount());
        log.info("Inventory updated for event: {}, less tickets: {}", order.getEventId(), order.getTicketCount());
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
