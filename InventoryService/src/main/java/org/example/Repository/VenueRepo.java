package org.example.Repository;

import org.example.Models.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueRepo extends JpaRepository<Venue,Long> {
}
