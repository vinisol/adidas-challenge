package com.app.manageitinerary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.manageitinerary.domain.Itinerary;


@Repository
public interface ItineraryRepository extends JpaRepository<Itinerary, Integer> {

}
