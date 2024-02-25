package authorization.servlet;

import authorization.model.ValidationResult;
import authorization.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/validate")
public class ValidateTokenServlet extends HttpServlet {
    private final AuthService authService = AuthService.getInstance();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getHeader("Authorization");
        boolean isValid = authService.verifyToken(token);
        ValidationResult result = new ValidationResult(isValid);
        String json = mapper.writeValueAsString(result);
        resp.setContentType("application/json");

        resp.getWriter().println(json);
        resp.getWriter().flush();
    }
}
