package price.util;

import lombok.Getter;

@Getter
public enum ExternalService {
    AUTH("http://localhost:8190/auth"),
    VALIDATE("http://localhost:8190/validate");

    private final String url;

    ExternalService(String url) {
        this.url = url;
    }
}
