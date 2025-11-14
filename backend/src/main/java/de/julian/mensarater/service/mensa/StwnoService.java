package de.julian.mensarater.service.mensa;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import de.julian.mensarater.dto.DishDTO;
import de.julian.mensarater.exception.MenuNotFoundException;
import de.julian.mensarater.model.Mensa;
import de.julian.mensarater.util.DateUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class StwnoService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Mensa mensa;

    private final Cache<String, List<DishDTO>> cache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.DAYS).maximumSize(10).build();

    private final OpenMensaApiService openMensaApiService;

    public List<DishDTO> getTodaysDishes() {
        logger.info("Loading menu for {}", mensa.getLocationString());
        int week = DateUtils.getYearWeek();
        String day = DateUtils.getCurrentDay();

        List<DishDTO> dishes = cache.getIfPresent(DateUtils.getCurrentDate());
        if (dishes != null) {
            logger.info("Returning cached menu for {}", mensa.getLocationString());
            return dishes;
        }
        logger.info("Fetching menu from stwno api for {}", mensa.getLocationString());
        String url = "http://www.stwno.de/infomax/daten-extern/csv/" + mensa.getLocationString() + "/" + week + ".csv";
        try {
            dishes = parseCsv(downloadCsv(url), day);
        } catch (IOException | URISyntaxException e) {
            this.logger.error("Error while loading menu for {}", mensa.getLocationString(), e);
            throw new MenuNotFoundException("Menu could not be loaded from the stwno api.");
        }
        cache.put(DateUtils.getCurrentDate(), dishes);
        return dishes;
    }

    private List<String[]> downloadCsv(String urlString) throws IOException, URISyntaxException {
        URI uri = new URI(urlString);
        URL url = uri.toURL();
        List<String[]> csvData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.ISO_8859_1))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                csvData.add(values);
            }
        }
        return csvData;
    }

    private List<DishDTO> parseCsv(List<String[]> csvData, String day) {
        List<DishDTO> dishes = new ArrayList<>();
        for (String[] values : csvData) {
            if (values[1].equals(day) && values[2].startsWith("HG")) {
                DishDTO dish = new DishDTO();
                dish.setDishName(values[3].split("\\(")[0].trim());
                dish.setPrice(Double.parseDouble(values[6].replaceAll(",", ".")));
                dish.setRating(-1);
                dish.setWarning(false);
                dishes.add(dish);
            }
        }
        return dishes;
    }
}
