package order.util;

import lombok.Getter;

@Getter
public enum ExternalService {
    CALCULATE("http://localhost:8082/calculate");

    private final String url;

    ExternalService(String url) {
        this.url = url;
    }
}