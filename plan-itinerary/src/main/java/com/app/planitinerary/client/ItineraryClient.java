package com.app.planitinerary.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.app.planitinerary.domain.Itinerary;


@FeignClient(value = "itinerary", fallback = ItineraryClientFallback.class)
public interface ItineraryClient {

    @GetMapping(value = "itineraries")
    List<Itinerary> getItineraries();
}
