package org.example.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VenueInventoryResponse {
    private Long venueId;
    private String venueName;
    private String address;
    private Long totalCapacity;
}
