package price.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalTime;

public class JsonOrderParser {
    private final ObjectMapper mapper = new ObjectMapper();

    public String parseCarCategory(String jsonData) throws IOException {
        JsonNode rootNode = mapper.readTree(jsonData);
        return rootNode.get("serviceType").asText();
    }

    public String parseTimeOfDay(String jsonOrder) throws IOException {
        JsonNode node = mapper.readTree(jsonOrder);
        String orderTime = node.get("orderTime").asText();
        for (TimeOfDay timeOfDay : TimeOfDay.values()) {
            if (isInTimeRange(orderTime, timeOfDay)) {
                return timeOfDay.name();
            }
        }
        throw new IllegalArgumentException("Invalid order time: " + orderTime);
    }

    private boolean isInTimeRange(String orderTime, TimeOfDay timeOfDay) {
        LocalTime time = LocalTime.parse(orderTime);
        if (timeOfDay == TimeOfDay.NIGHT) {
            return time.isAfter(timeOfDay.getStartTime()) || time.equals(LocalTime.MIDNIGHT) || time.isBefore(timeOfDay.getEndTime());
        } else {
            return !time.isBefore(timeOfDay.getStartTime()) && !time.isAfter(timeOfDay.getEndTime());
        }
    }

    public double parseDistance(String jsonData) throws IOException {
        JsonNode rootNode = mapper.readTree(jsonData);
        return rootNode.get("distance").asDouble();
    }

    public String parseToken(String jsonData) throws IOException {
        JsonNode rootNode = mapper.readTree(jsonData);
        JsonNode tokenNode = rootNode.get("token");
        return tokenNode != null ? tokenNode.asText() : null;
    }
}