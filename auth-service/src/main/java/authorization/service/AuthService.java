package authorization.service;

import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private static final AuthService INSTANCE = new AuthService();

    public static AuthService getInstance() {
        return INSTANCE;
    }

    private final Map<String, String> userDB = new HashMap<>();
    private final Map<String, String> tokenDB = new HashMap<>();

    public String newUserRegistration(String userName, String password) {
        if (userDuplicateCheck(userName)) {
            return null;
        }
        String token = generateToken();
        userDB.put(userName, password);
        tokenDB.put(token, userName);
        return token;
    }

    private boolean userDuplicateCheck(String userName) {
        return userDB.containsKey(userName);
    }

    private String generateToken() {
        return "discount" + System.currentTimeMillis();
    }

    public boolean authentication(String userName, String password) {
        if (userDB.containsKey(userName) && userDB.get(userName).equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verifyToken(String token) {
        return tokenDB.containsKey(token);
    }
}