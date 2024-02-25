package price.sevlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import price.sevice.PriceService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/calculate")
public class PriceServlet extends HttpServlet {
    private final PriceService priceService = PriceService.getInstance();
    private static final Logger log = Logger.getLogger(PriceServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.log(Level.INFO, "About to process post request: " + req.getParameterMap());
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        String jsonOrder = sb.toString();
        double price = priceService.totalOrderPrice(jsonOrder);
        PrintWriter out = resp.getWriter();
        out.println("Price for the ride: $" + price);
        out.close();
    }
}