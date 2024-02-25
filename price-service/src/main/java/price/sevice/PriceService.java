package price.sevice;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import price.models.JsonOrderParser;
import price.models.TimeOfDay;
import price.pricelists.*;
import price.util.ExternalService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PriceService {
    private final ObjectMapper mapper = new ObjectMapper();
    private static final PriceService INSTANCE = new PriceService();
    private final CarCategoryPrice carCategoryPrice = new CarCategoryPrice();
    private final CoefficientPriceByCarCategory distancePriceByCarCategory = new CoefficientPriceByCarCategory();
    private final TimeOfDayPrice timeOfDayPrice = new TimeOfDayPrice();
    private final DiscountToken discountToken = new DiscountToken();
    private final JsonOrderParser jsonOrderParser = new JsonOrderParser();

    public static PriceService getInstance() {
        return INSTANCE;
    }

    public double totalOrderPrice(String jsonOrder) throws IOException {
        String carCategory = jsonOrderParser.parseCarCategory(jsonOrder);
        String orderTime = jsonOrderParser.parseTimeOfDay(jsonOrder);
        double distance = jsonOrderParser.parseDistance(jsonOrder);
        String token = jsonOrderParser.parseToken(jsonOrder);

        double minPrice = calculateMinPrice(carCategory, orderTime);
        double pricePerKilometer = calculatePricePerKilometer(carCategory, orderTime);
        double totalPrice = getCarCategoryPrice(carCategory) + (pricePerKilometer * distance);

        if (totalPrice < minPrice) {
            totalPrice = minPrice;
        }

        if (isValidToken(token)) {
            totalPrice *= discountToken.getDiscountToken();
        }
        return totalPrice;
    }

    private double calculateMinPrice(String carCategory, String orderTime) {
        return getCarCategoryPrice(carCategory) + getTimeOfDayPrice(orderTime);
    }

    private double calculatePricePerKilometer(String carCategory, String orderTime) {
        return getDistanceCoefficient(carCategory) * getDistancePriceByTimeOfDay(orderTime);
    }

    private double getDistancePriceByTimeOfDay(String orderTime) {
        TimeOfDay timeOfDay = TimeOfDay.valueOf(orderTime.toUpperCase());
        DistancePriceByTimeOfDay distancePriceByTimeOfDay = new DistancePriceByTimeOfDay();

        switch (timeOfDay) {
            case MORNING:
                return distancePriceByTimeOfDay.getMorningKilometer();
            case DAY:
                return distancePriceByTimeOfDay.getDayKilometer();
            case EVENING:
                return distancePriceByTimeOfDay.getEveningKilometer();
            case NIGHT:
                return distancePriceByTimeOfDay.getNightKilometer();
            default:
                throw new IllegalArgumentException("Invalid time of day: " + orderTime);
        }
    }

    private double getCarCategoryPrice(String carCategory) {
        String category = carCategory.toLowerCase();
        switch (category) {
            case "economy":
                return carCategoryPrice.getCarEconomyPrice();
            case "standard":
                return carCategoryPrice.getCarStandardPrice();
            case "comfort":
                return carCategoryPrice.getCarComfortPrice();
            default:
                throw new IllegalArgumentException("Invalid car category: " + carCategory);
        }
    }


    private double getTimeOfDayPrice(String orderTime) {
        TimeOfDay timeOfDay = TimeOfDay.valueOf(orderTime.toUpperCase());
        switch (timeOfDay) {
            case MORNING:
                return timeOfDayPrice.getMorningPrice();
            case DAY:
                return timeOfDayPrice.getDayPrice();
            case EVENING:
                return timeOfDayPrice.getEveningPrice();
            case NIGHT:
                return timeOfDayPrice.getNightPrice();
            default:
                throw new IllegalArgumentException("Invalid time of day: " + orderTime);
        }
    }

    private double getDistanceCoefficient(String carCategory) {
        String category = carCategory.toLowerCase();
        return switch (category) {
            case "economy" -> distancePriceByCarCategory.getEconomyKilometer();
            case "standard" -> distancePriceByCarCategory.getStandardKilometer();
            case "comfort" -> distancePriceByCarCategory.getComfortKilometer();
            default -> throw new IllegalArgumentException("Invalid car category: " + carCategory);
        };
    }

    private boolean isValidToken(String token) {
        try {
            URL url = new URL(ExternalService.VALIDATE.getUrl());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", token);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = in.readLine();
                System.out.println(response);
                in.close();
                JsonNode jsonResponse = mapper.readTree(response);
                return jsonResponse.get("valid").asBoolean();
            } else {
                System.out.println("Server returned non-OK status: " + responseCode);
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}