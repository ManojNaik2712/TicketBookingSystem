package org.example.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.Models.Venue;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventInventoryRespone {
    private String event;
    private Long totalCapacity;
    private Venue venue;
}
