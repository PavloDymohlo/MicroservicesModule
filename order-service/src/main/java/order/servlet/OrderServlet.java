package order.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import order.util.ExternalService;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import order.model.Car;
import order.model.Order;
import order.service.CarService;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final CarService carService = CarService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        double distance = Double.parseDouble(req.getParameter("distance"));
        String time = req.getParameter("time");
        String service = req.getParameter("service");
        String token = req.getParameter("token");
        Order order = new Order(distance, time, service, token);
        sendToCalculationServer(order, req, resp);
    }

    private void sendToCalculationServer(Order order, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(ExternalService.CALCULATE.getUrl());
        String jsonOrder = mapper.writeValueAsString(order);
        StringEntity entity = new StringEntity(jsonOrder);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        HttpResponse response = httpClient.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            String priceJson = EntityUtils.toString(response.getEntity());
            Car nearestCar = carService.nearestCarSimulation(order.getServiceType());
            String category = nearestCar.getCarCategory().toString();
            String brand = nearestCar.getModel();
            req.setAttribute("price", priceJson);
            req.setAttribute("category", category);
            req.setAttribute("brand", brand);
            RequestDispatcher dispatcher = req.getRequestDispatcher("jsp/order.jsp");
            dispatcher.forward(req, resp);
        } else {
            resp.sendError(statusCode, "Error occurred during price calculation.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("jsp/order.jsp").forward(req, resp);
    }
}