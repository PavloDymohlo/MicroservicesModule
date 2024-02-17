package authorization.util;

import lombok.Getter;

@Getter
public enum ExternalService {
    ORDER("http://localhost:8081/order");

    private final String url;
    ExternalService(String url) {
        this.url = url;
    }
}
