package com.app.planitinerary.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.planitinerary.domain.Itinerary;
import com.app.planitinerary.domain.PlanningCriteria;
import com.app.planitinerary.service.PlanningService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(description="REST API for Planning shortest way")
@RestController
@RequestMapping("/plannings")
public class PlanningController {

    private final PlanningService itineraryService;

    public PlanningController(PlanningService itineraryService) {this.itineraryService = itineraryService;}

    @ApiOperation(
        value = "Return shortest way according to planning criteria",
        httpMethod = "GET",
        response = Itinerary[].class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 400, message = "Request invalid or malformed"),
        @ApiResponse(code = 404, message = "Resource not found"),
        @ApiResponse(code = 500, message = "Internal Server error")
    })
    @GetMapping
    public ResponseEntity get(@RequestParam(name = "from") String departureCity, @RequestParam(name = "to") String arrivalCity,
                              @RequestParam(name = "criteria", required = false, defaultValue = "TIME") PlanningCriteria criteria) {
        List<Itinerary> itineraries =  itineraryService.plan(departureCity, arrivalCity, criteria);

        return Optional.ofNullable(itineraries)
            .map(i -> i.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(i))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
