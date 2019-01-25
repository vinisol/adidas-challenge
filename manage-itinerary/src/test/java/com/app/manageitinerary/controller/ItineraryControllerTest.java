package com.app.manageitinerary.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.app.manageitinerary.domain.Itinerary;
import com.app.manageitinerary.repository.ItineraryRepository;


@RunWith(SpringRunner.class)
@WebMvcTest(ItineraryController.class)
public class ItineraryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItineraryRepository itineraryRepository;

    @Test
    public void whenGet_thenReturnOk() throws Exception {
        when(itineraryRepository.findAll()).thenReturn(getItineraries());
        mockMvc.perform(get("/itineraries")
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
        verify(itineraryRepository).findAll();
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
