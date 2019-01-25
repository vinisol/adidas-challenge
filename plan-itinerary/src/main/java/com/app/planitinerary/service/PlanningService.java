package com.app.planitinerary.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.app.planitinerary.client.ItineraryClient;
import com.app.planitinerary.domain.Edge;
import com.app.planitinerary.domain.City;
import com.app.planitinerary.domain.Itinerary;
import com.app.planitinerary.domain.PlanningCriteria;


@Service
public class PlanningService {

    private final ItineraryClient itineraryClient;

    public PlanningService(ItineraryClient itineraryClient) {this.itineraryClient = itineraryClient;}

    public List<Itinerary> plan(String departureCity, String arrivalCity, PlanningCriteria criteria) {
        Assert.notNull(departureCity, "Departure city must not be null");
        Assert.notNull(arrivalCity, "Arrival city must not be null");

        List<Itinerary> itineraries = itineraryClient.getItineraries();
        Map<String, City> mapItineraries = buildMapItineraries(itineraries, criteria);
        City source = mapItineraries.get(departureCity);
        City destination = mapItineraries.get(arrivalCity);

        if (source != null && destination != null) {
            source.computeShortestPaths();
            return chooseShortestWay(itineraries, destination);
        }

        return Collections.emptyList();
    }

    Map<String, City> buildMapItineraries(List<Itinerary> itineraries, PlanningCriteria criteria) {
        Assert.notNull(itineraries, "Itineraries must not be null");

        Map<String, City> map = new HashMap<>();
        for (Itinerary itinerary : itineraries) {
            City sourceVertex, destinationVertex;
            String source = itinerary.getDepartureCity();
            String destination = itinerary.getArrivalCity();

            if (!map.containsKey(source)) {
                sourceVertex = new City(source);
                map.put(source, sourceVertex);
            }
            if (!map.containsKey(destination)) {
                destinationVertex = new City(destination);
                map.put(destination, destinationVertex);
            }
            sourceVertex = map.get(source);
            destinationVertex = map.get(destination);

            long duration = calculateDifferenceInMinutes(itinerary.getDepartureTime(), itinerary.getArrivalTime());
            long weight = criteria.equals(PlanningCriteria.TIME) ? duration : 1;
            sourceVertex.addNeighbour(new Edge(sourceVertex, destinationVertex, weight));
        }
        return map;
    }

    List<Itinerary> chooseShortestWay(List<Itinerary> itineraries, City destinationVertex) {
        List<Itinerary> path = new ArrayList<>();
        for (City vertex = destinationVertex; vertex != null && vertex.getPrevious() != null; vertex = vertex.getPrevious()) {
            String vertexName = vertex.getName();
            String predecessorName = vertex.getPrevious().getName();

            path.add(itineraries.stream()
                .filter(i -> i.getDepartureCity().equalsIgnoreCase(predecessorName)
                    && i.getArrivalCity().equalsIgnoreCase(vertexName))
                .findAny()
                .orElse(null));
        }
        Collections.reverse(path);
        return path;
    }

    long calculateDifferenceInMinutes(LocalDateTime start, LocalDateTime end) {
        Assert.notNull(start, "Start date must not be null!");
        Assert.notNull(end, "End date must not be null!");
        Assert.isTrue(start.isBefore(end), "Start date must be before End date");

        return Duration.between(start, end).toMinutes();
    }
}
