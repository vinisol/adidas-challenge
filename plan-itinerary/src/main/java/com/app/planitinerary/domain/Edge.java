package com.app.planitinerary.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class Edge {

    private City source;

    private City destination;

    private long weight;
}
