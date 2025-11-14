package de.julian.mensarater.service;

import de.julian.mensarater.dto.DishDTO;
import de.julian.mensarater.entity.Dish;
import de.julian.mensarater.entity.MensaUser;
import de.julian.mensarater.entity.OpenMensa;
import de.julian.mensarater.entity.Rating;
import de.julian.mensarater.model.AvgRatingModel;
import de.julian.mensarater.repository.DishRepository;
import de.julian.mensarater.repository.OpenMensaRepository;
import de.julian.mensarater.repository.RatingRepository;
import de.julian.mensarater.repository.UserRepository;
import de.julian.mensarater.service.mensa.OpenMensaApiService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OpenMensaApiService mensaService;

    private final DishRepository dishRepository;

    private final RatingRepository ratingRepository;

    private final UserRepository userRepository;

    private final OpenMensaRepository openMensaRepository;

    public List<DishDTO> getUserRatingsForDate(String userEmail, Instant date) {
        MensaUser mensaUser = userRepository.findByEmail(userEmail);
        logger.info("Fetching user ratings in mensa: {}", mensaUser.getOpenMensa().getMensaId());
        List<DishDTO> today = mensaService.getMensaDishesForDate(mensaUser.getOpenMensa().getMensaId(), date);
        List<String> todayNames = today.stream().map(DishDTO::getDishName).toList();
        List<Rating> userRatings = ratingRepository.findByUserEmailAndDishNamesAndMensa(userEmail, todayNames, mensaUser.getOpenMensa());
        for (Rating r: userRatings) {
            DishDTO matching = today.stream().filter(dish -> dish.getDishName().equals(r.getDish().getName())).findFirst().orElse(null);
            if (matching == null) continue;
            int index = today.indexOf(matching);
            matching.setRating(r.getRating());
            matching.setWarning(r.isWarning());
            today.set(index, matching);
        }
        return today;
    }

    public List<DishDTO> getAvgRatingsForDate(long openMensaId, Instant date) {
        OpenMensa mensa = openMensaRepository.findOpenMensaByMensaId(openMensaId);
        logger.info("Fetching average ratings for mensa: {}", mensa.getMensaId());
        List<DishDTO> today = mensaService.getMensaDishesForDate(mensa.getMensaId(), date);
        List<String> todayNames = today.stream().map(DishDTO::getDishName).toList();
        List<AvgRatingModel> avgRatings = ratingRepository.findAverageRatingByDishNamesAndMensa(todayNames, mensa);
        for (AvgRatingModel a: avgRatings) {
            DishDTO matching = today.stream().filter(dish -> dish.getDishName().equals(a.getName())).findFirst().orElse(null);
            if (matching == null) continue;
            int index = today.indexOf(matching);
            assert index != -1;
            matching.setRating(a.getAvg());
            matching.setWarning(a.isWarning());
            today.set(index, matching);
        }
        return today;
    }

    public DishDTO saveRating(DishDTO dishDto, String email) {
        MensaUser mensaUser = userRepository.findByEmail(email);
        logger.info("Saving rating for dish: {} in mensa: {}", dishDto.getDishName(), mensaUser.getOpenMensa().getMensaId());
        dishDto.setUser(email);

        Dish dish = dishRepository.findByName(dishDto.getDishName());
        if (dish == null) {
            logger.info("Creating new dish: {} for mensa: {}", dishDto.getDishName(), mensaUser.getOpenMensa().getName());
            dish = new Dish();
            dish.setName(dishDto.getDishName());
            dish.setOpenMensa(mensaUser.getOpenMensa());
            dish = dishRepository.save(dish);
        }

        Rating rating = ratingRepository.findByMensaUserAndDish(mensaUser, dish);
        if (rating == null) {
            logger.info("Creating new rating for dish: {}", dish.getName());
            rating = new Rating();
            rating.setMensaUser(mensaUser);
            rating.setDish(dish);
        }
        rating.setRating(dishDto.getRating());
        rating.setWarning(dishDto.isWarning());
        DishDTO savedDish = mapRatingToDish(ratingRepository.save(rating));
        savedDish.setPrice(dishDto.getPrice());
        return savedDish;
    }

    private DishDTO mapRatingToDish(Rating rating) {
        DishDTO dish = new DishDTO();
        dish.setDishName(rating.getDish().getName());
        dish.setUser(rating.getMensaUser().getEmail());
        dish.setPrice(0);
        dish.setRating(rating.getRating());
        dish.setWarning(rating.isWarning());

        return dish;
    }
}
