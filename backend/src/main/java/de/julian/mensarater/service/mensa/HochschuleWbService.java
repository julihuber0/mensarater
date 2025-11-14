package de.julian.mensarater.service.mensa;

import de.julian.mensarater.dto.DishDTO;
import de.julian.mensarater.model.Mensa;
import de.julian.mensarater.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public abstract class HochschuleWbService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Mensa mensa;
    private final static String CSV_PATH = System.getenv("CSV_PATH") != null ? System.getenv("CSV_PATH") : "/scrape/csv";

    protected HochschuleWbService(Mensa mensa) {
        this.mensa = mensa;
    }

    public List<DishDTO> getTodaysDishes() {
        logger.info("Loading menu for {}", mensa.getLocationString());
        return parseCSV(getCSVPath());
    }

    private Path getCSVPath() {
        String fileName = mensa.getLocationString() + "_" + DateUtils.getCurrentDate().replaceAll("\\.", "_") + ".csv";
        return Path.of(CSV_PATH, fileName);
    }

    private List<DishDTO> parseCSV(Path path) {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return reader.lines().skip(1).map(line -> line.split(",")).map(line -> {
                DishDTO dish = new DishDTO();
                dish.setDishName(line[0].trim());
                dish.setPrice(Double.parseDouble(line[1]));
                dish.setRating(-1);
                dish.setWarning(false);
                return dish;
            }).collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("Error while loading menu for {}: {}", mensa.getLocationString(), e.getMessage());
            return List.of();
        }
    }
}
