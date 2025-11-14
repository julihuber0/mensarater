package de.julian.mensarater.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AvgRatingModel {

    private String name;

    private double avg;

    private boolean warning;
}
