package com.restaurant.acquerello.repositories;

import com.restaurant.acquerello.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface BookingRepository extends JpaRepository<Booking, Long> {
    public Booking getBydId(Long id);
}
