package org.example.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryResponse {
    private Long eventId;
    private String event;
    private Long totalCapacity;
    private VenueResponse venue;
    private BigDecimal ticketPrice;
}
