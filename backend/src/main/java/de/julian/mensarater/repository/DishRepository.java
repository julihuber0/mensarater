package de.julian.mensarater.repository;

import de.julian.mensarater.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish, Long> {
    Dish findByName(String name);
}
