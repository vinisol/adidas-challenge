package com.app.planitinerary.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.app.planitinerary.client.ItineraryClient;
import com.app.planitinerary.domain.Itinerary;
import com.app.planitinerary.domain.PlanningCriteria;
import com.app.planitinerary.domain.City;


@RunWith(MockitoJUnitRunner.class)
public class PlanningServiceTest {

    @Mock
    private ItineraryClient itineraryClient;

    @InjectMocks
    private PlanningService planningService;

    @Test
    public void givenTwoDates_whenCalculateDifferenceInMinutes_thenReturnMinutes() {
        long expected = 120;
        LocalDateTime now = LocalDateTime.now();

        long actual = planningService.calculateDifferenceInMinutes(now, now.plusHours(2));

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void givenItinerariesAndPlanningByTime_whenBuildMapItineraries_thenReturnMap() {
        List<Itinerary> itineraries = getItineraries();

        Map<String, City> mapItineraries = planningService.buildMapItineraries(itineraries, PlanningCriteria.TIME);

        assertThat(mapItineraries).isNotNull();
        assertThat(mapItineraries).isNotEmpty().hasSize(6);
        assertThat(mapItineraries.get("Berlin").getAdjacenciesList()).isNotEmpty().hasSize(2);
        assertThat(mapItineraries.get("Berlin").getAdjacenciesList()).extracting("weight").contains(90L, 110L);
        assertThat(mapItineraries.get("Roma").getAdjacenciesList()).isNotEmpty().hasSize(1);
        assertThat(mapItineraries.get("Roma").getAdjacenciesList()).extracting("weight").contains(160L);
    }

    @Test
    public void givenItinerariesAndPlanningByConnections_whenBuildMapItineraries_thenReturnMap() {
        List<Itinerary> itineraries = getItineraries();

        Map<String, City> mapItineraries = planningService.buildMapItineraries(itineraries, PlanningCriteria.CONNECTIONS);

        assertThat(mapItineraries).isNotNull();
        assertThat(mapItineraries).isNotEmpty().hasSize(6);
        assertThat(mapItineraries.get("Berlin").getAdjacenciesList()).isNotEmpty().hasSize(2);
        assertThat(mapItineraries.get("Berlin").getAdjacenciesList()).extracting("weight").contains(1L, 1L);
        assertThat(mapItineraries.get("Roma").getAdjacenciesList()).isNotEmpty().hasSize(1);
        assertThat(mapItineraries.get("Roma").getAdjacenciesList()).extracting("weight").contains(1L);
    }

    @Test
    public void givenItinerariesAndPlanningByConnections_whenChooseShortestWay_thenReturnLisItineraries() {
        when(itineraryClient.getItineraries()).thenReturn(getItineraries());

        List<Itinerary> shortestWay = planningService.plan("Berlin", "Madrid", PlanningCriteria.TIME);

        assertThat(shortestWay).isNotNull();
        assertThat(shortestWay).isNotEmpty().hasSize(2);
        assertThat(shortestWay).extracting("arrivalCity").contains("Brussels", "Madrid");
    }

    @Test
    public void givenCitiesAndPlanningByTime_whenPlan_thenReturnShortestWay() {
        when(itineraryClient.getItineraries()).thenReturn(getItineraries());

        List<Itinerary> shortestWay = planningService.plan("Paris", "Berlin", PlanningCriteria.TIME);

        assertThat(shortestWay).isNotNull();
        assertThat(shortestWay).isNotEmpty().hasSize(3);
        assertThat(shortestWay).extracting("arrivalCity").containsExactly("Brussels", "Madrid", "Berlin");
    }

    @Test
    public void givenCitiesAndPlanningByConnections_whenPlan_thenReturnShortestWay() {
        when(itineraryClient.getItineraries()).thenReturn(getItineraries());

        List<Itinerary> shortestWay = planningService.plan("Amsterdam", "Berlin", PlanningCriteria.CONNECTIONS);

        assertThat(shortestWay).isNotNull();
        assertThat(shortestWay).isNotEmpty().hasSize(2);
        assertThat(shortestWay).extracting("arrivalCity").containsExactly("Madrid", "Berlin");
    }

    @Test
    public void givenNonExistentCitiesAndPlanningByConnections_whenPlan_thenReturnEmptyList() {
        when(itineraryClient.getItineraries()).thenReturn(getItineraries());

        List<Itinerary> shortestWay = planningService.plan("Barcelona", "Berlin", PlanningCriteria.CONNECTIONS);

        assertThat(shortestWay).isNotNull();
        assertThat(shortestWay).isEmpty();
    }


    private List<Itinerary> getItineraries() {
        return Arrays.asList(
            new Itinerary(1,
                "Berlin",
                "Brussels",
                LocalDateTime.parse("2019-10-14T11:05:00"),
                LocalDateTime.parse("2019-10-14T12:35:00")),
            new Itinerary(2,
                "Berlin",
                "Paris",
                LocalDateTime.parse("2019-10-14T09:25:00"),
                LocalDateTime.parse("2019-10-14T11:15:00")),
            new Itinerary(3,
                "Brussels",
                "Madrid",
                LocalDateTime.parse("2019-10-14T14:35:00"),
                LocalDateTime.parse("2019-10-14T16:50:00")),
            new Itinerary(4,
                "Paris",
                "Brussels",
                LocalDateTime.parse("2019-10-14T07:25:00"),
                LocalDateTime.parse("2019-10-14T08:25:00")),
            new Itinerary(5,
                "Paris",
                "Roma",
                LocalDateTime.parse("2018-10-14T21:20:00"),
                LocalDateTime.parse("2018-10-14T23:25:00")),
            new Itinerary(6,
                "Roma",
                "Madrid",
                LocalDateTime.parse("2019-10-14T18:25:00"),
                LocalDateTime.parse("2019-10-14T21:05:00")),
            new Itinerary(7,
                "Amsterdam",
                "Roma",
                LocalDateTime.parse("2019-10-14T16:55:00"),
                LocalDateTime.parse("2019-10-14T19:10:00")),
            new Itinerary(8,
                "Amsterdam",
                "Madrid",
                LocalDateTime.parse("2019-10-14T07:20:00"),
                LocalDateTime.parse("2019-10-14T10:00:00")),
            new Itinerary(9,
                "Madrid",
                "Berlin",
                LocalDateTime.parse("2019-10-14T12:20:00"),
                LocalDateTime.parse("2019-10-14T15:25:00"))
        );
   }
}
