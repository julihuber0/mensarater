package de.julian.mensarater.model;

import de.julian.mensarater.dto.DishDTO;

public record OpenMensaDishModel(String name, String category,
                                 de.julian.mensarater.model.OpenMensaDishModel.Prices prices) {

    public DishDTO toDishDTO() {
        DishDTO dishDTO = new DishDTO();
        dishDTO.setDishName(name);
        dishDTO.setCategory(category);
        dishDTO.setPrice(prices.students);
        dishDTO.setWarning(false); // Assuming no warning by default
        dishDTO.setRating(-1); // Assuming no rating by default
        return dishDTO;
    }

    private record Prices(double students, double employees, double others) {
    }
}
