package com.app.manageitinerary.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.manageitinerary.domain.Itinerary;
import com.app.manageitinerary.repository.ItineraryRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(description="REST API for Itinerary")
@RestController
@RequestMapping("/itineraries")
public class ItineraryController {

    private final ItineraryRepository itineraryRepository;

    public ItineraryController(ItineraryRepository itineraryRepository) {this.itineraryRepository = itineraryRepository;}

    @ApiOperation(
        value = "Get all itineraries",
        httpMethod = "GET",
        response = Itinerary[].class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 404, message = "Resource not found"),
        @ApiResponse(code = 500, message = "Internal Server error")
    })
    @GetMapping
    public ResponseEntity get() {
        return ResponseEntity.ok(itineraryRepository.findAll());
    }
}
