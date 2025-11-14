package de.julian.mensarater.repository;

import de.julian.mensarater.entity.Dish;
import de.julian.mensarater.entity.MensaUser;
import de.julian.mensarater.entity.OpenMensa;
import de.julian.mensarater.entity.Rating;
import de.julian.mensarater.model.AvgRatingModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("SELECT r FROM Rating r WHERE r.mensaUser.email = :email AND r.dish.name IN :dishNames AND r.dish.openMensa = :mensa")
    List<Rating> findByUserEmailAndDishNamesAndMensa(@Param("email") String email, @Param("dishNames") List<String> dishNames, @Param("mensa") OpenMensa mensa);

    @Query("SELECT new de.julian.mensarater.model.AvgRatingModel(r.dish.name, AVG(r.rating), BOOL_AND(r.warning)) as avg FROM Rating r WHERE r.dish.name IN :dishNames AND r.dish.openMensa = :mensa GROUP BY r.dish.name")
    List<AvgRatingModel> findAverageRatingByDishNamesAndMensa(@Param("dishNames") List<String> dishNames, @Param("mensa") OpenMensa mensa);

    Rating findByMensaUserAndDish(MensaUser mensaUser, Dish dish);
}
