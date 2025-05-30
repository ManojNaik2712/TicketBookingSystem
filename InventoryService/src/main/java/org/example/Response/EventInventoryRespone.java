package org.example.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.Models.Venue;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventInventoryRespone {
    private Long eventId;
    private String event;
    private Long totalCapacity;
    private Venue venue;
    private BigDecimal ticketPrice;
}
