package com.app.planitinerary.domain;

import java.time.LocalDateTime;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Api("Intinerary entity")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Itinerary {

    @ApiModelProperty(value = "Database generated itinerary ID", dataType = "Integer")
    private Integer id;

    @ApiModelProperty(value = "Name of the origin city", name = "departureCity", required = true, dataType="String")
    private String departureCity;

    @ApiModelProperty(value = "Name of the destiny city", name = "arrivalCity", required = true, dataType="String")
    private String arrivalCity;

    @ApiModelProperty(value = "Date and time of departure", name = "departureTime", required = true, dataType="LocalDateTime")
    private LocalDateTime departureTime;

    @ApiModelProperty(value = "Date and time of arrival", name = "arrivalTime", required = true, dataType="LocalDateTime")
    private LocalDateTime arrivalTime;
}
