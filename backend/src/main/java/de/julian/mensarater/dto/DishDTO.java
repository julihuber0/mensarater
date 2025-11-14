package de.julian.mensarater.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishDTO {

    private String user;

    private String dishName;

    private String category;

    private double price;

    private double rating;

    private boolean warning;
}
