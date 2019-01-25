package com.app.planitinerary.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.app.planitinerary.domain.Itinerary;
import com.app.planitinerary.domain.PlanningCriteria;
import com.app.planitinerary.service.PlanningService;


@RunWith(SpringRunner.class)
@WebMvcTest(PlanningController.class)
public class PlanningControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlanningService itineraryService;

    @Test
    public void givenURLMissingAllParameters_whenGet_thenReturnBadRequest() throws Exception {
        mockMvc.perform(get("/plannings")
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest());
        verifyZeroInteractions(itineraryService);
    }

    @Test
    public void givenURLMissingOneParameter_whenGet_thenReturnBadRequest() throws Exception {
        mockMvc.perform(get("/plannings?to=Berlin")
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest());
        verifyZeroInteractions(itineraryService);
    }

    @Test
    public void givenInvalidCriteria_whenGet_thenReturnBadRequest() throws Exception {
        mockMvc.perform(get("/plannings?from=Berlin&to=Amsterdam&criteria=XXX")
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest());
        verifyZeroInteractions(itineraryService);
    }

    @Test
    public void givenParameters_whenGet_thenReturnNullNotFound() throws Exception {
        String from = "Berlin";
        String to = "Amsterdam";

        when(itineraryService.plan(from, to, PlanningCriteria.TIME)).thenReturn(null);

        mockMvc.perform(get("/plannings?from=Berlin&to=Amsterdam&criteria=TIME")
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNotFound());
        verify(itineraryService).plan(from, to, PlanningCriteria.TIME);
        verifyNoMoreInteractions(itineraryService);
    }

    @Test
    public void givenParameters_whenGet_thenReturnEmptyListNotFound() throws Exception {
        String from = "Berlin";
        String to = "Amsterdam";

        when(itineraryService.plan(from, to, PlanningCriteria.TIME)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/plannings?from=Berlin&to=Amsterdam&criteria=TIME")
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNotFound());
        verify(itineraryService).plan(from, to, PlanningCriteria.TIME);
        verifyNoMoreInteractions(itineraryService);
    }

    @Test
    public void givenParameters_whenGet_thenReturnOk() throws Exception {
        String from = "Berlin";
        String to = "Amsterdam";

        when(itineraryService.plan(from, to, PlanningCriteria.TIME)).thenReturn(Arrays.asList(createItinerary()));

        mockMvc.perform(get("/plannings?from=Berlin&to=Amsterdam&criteria=TIME")
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
        verify(itineraryService).plan(from, to, PlanningCriteria.TIME);
        verifyNoMoreInteractions(itineraryService);
    }

    private Itinerary createItinerary() {
        return new Itinerary(1, "Berlin", "Amsterdam", LocalDateTime.now(), LocalDateTime.now().plusHours(1));
    }
}
