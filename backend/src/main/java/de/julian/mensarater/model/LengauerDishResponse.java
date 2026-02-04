package de.julian.mensarater.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LengauerDishResponse {

    public long id;
    public String name;
    public String category;
    public double price;

    public static OpenMensaDishModel mapToOpenMensaDish(LengauerDishResponse response) {
        OpenMensaDishModel.Prices prices = new OpenMensaDishModel.Prices(response.price, 0.0, response.price + 1.5);
        return new OpenMensaDishModel(response.name, response.category, prices);
    }
}
