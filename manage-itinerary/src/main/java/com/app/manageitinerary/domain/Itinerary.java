package com.app.manageitinerary.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

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
@Entity
public class Itinerary {

    @ApiModelProperty(value = "Database generated itinerary ID", dataType = "Integer")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    private Integer id;

    @ApiModelProperty(value = "Name of the origin city", name = "departureCity", required = true, dataType="String")
    @NotEmpty(message = "Origin city must not be empty")
    private String departureCity;

    @ApiModelProperty(value = "Name of the destiny city", name = "arrivalCity", required = true, dataType="String")
    @NotEmpty(message = "Origin city must not be empty")
    private String arrivalCity;

    @ApiModelProperty(value = "Date and time of departure", name = "departureTime", required = true, dataType="LocalDateTime")
    @NotNull(message = "Departure time must not be null")
    private LocalDateTime departureTime;

    @ApiModelProperty(value = "Date and time of arrival", name = "arrivalTime", required = true, dataType="LocalDateTime")
    @NotNull(message = "Arrival time must not be null")
    private LocalDateTime arrivalTime;
}
