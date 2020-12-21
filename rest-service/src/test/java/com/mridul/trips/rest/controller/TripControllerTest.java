package com.mridul.trips.rest.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.mridul.trips.rest.cache.TripCountCache;
import com.mridul.trips.service.TripService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebMvcTest(TripController.class)
public class TripControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TripService tripService;

    @MockBean
    private TripCountCache countCache;

    @Test
    public void testTripCounts() throws Exception {
        LocalDate date = LocalDate.now();
        String dateStr = DateTimeFormatter.ISO_DATE.format(date);
        when(tripService.getTripCount("a", date)).thenReturn(16);
        when(tripService.getTripCount("b", date)).thenReturn(0);
        mockMvc.perform(get("/v1/trips/counts")
                .param("medallions", "a", "b", "a")
                .param("pickupDate", dateStr)
                .param("ignoreCache", Boolean.TRUE.toString())
        ).andDo(print())
         .andExpect(status().isOk())
         .andExpect(jsonPath("$", hasSize(2)))
         .andExpect(jsonPath("$[0].medallion", is("a")))
         .andExpect(jsonPath("$[0].pickUpDate", is(dateStr)))
         .andExpect(jsonPath("$[0].trips", is(16)))
         .andExpect(jsonPath("$[1].medallion", is("b")))
         .andExpect(jsonPath("$[1].pickUpDate", is(dateStr)))
         .andExpect(jsonPath("$[1].trips", is(0)));
    }
}
