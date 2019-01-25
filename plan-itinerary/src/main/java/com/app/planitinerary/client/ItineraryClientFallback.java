package com.app.planitinerary.client;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;


@Component
public class ItineraryClientFallback implements ItineraryClient {

    @Override
    public List getItineraries() {
        return Collections.emptyList();
    }
}
