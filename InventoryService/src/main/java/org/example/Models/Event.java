package org.example.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "event")
public class Event {
    @Id
    private Long id;
    private String name;
    private Long totalCapacity;
    private Long leftCapacity;
    private BigDecimal ticketPrice;
    @ManyToOne
    @JoinColumn
    private Venue venue;

}
