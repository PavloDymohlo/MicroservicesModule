package authorization.servlet;

import authorization.service.AuthService;
import authorization.util.ExternalService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    private final AuthService authService = AuthService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action != null && !action.isEmpty()) {
            switch (action) {
                case "register":
                    registration(req, resp);
                    break;
                case "login":
                    loginIn(req, resp);
                    break;
                default:
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().println("Invalid action");
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Action parameter is missing");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("jsp/auth.jsp").forward(req, resp);
    }

    private void registration(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        String token = authService.newUserRegistration(userName, password);
        if (token != null) {
            resp.getWriter().println("Registration successful. Token: " + token);
        } else {
            resp.getWriter().println("User with this name already exists in the database.");
        }
    }

    private void loginIn(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");

        if (authService.authentication(userName, password)) {
            resp.sendRedirect(ExternalService.ORDER.getUrl());
        } else {
            resp.getWriter().println("The user is not registered.");
        }
    }
}
