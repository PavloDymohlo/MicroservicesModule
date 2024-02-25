package order.service;

import order.model.Car;

import java.util.ArrayList;
import java.util.List;

public class CarService {
    private static final CarService INSTANCE = new CarService();

    public static CarService getInstance() {
        return INSTANCE;
    }

    private final List<Car> cars = new ArrayList<>();

    private CarService() {
        CarServiceUtil.addCarsToList(cars);
    }

    public Car nearestCarSimulation(String category) {
        List<Car> filterByCategory = new ArrayList<>();
        for (Car car : cars) {
            if (car.getCarCategory().toString().equalsIgnoreCase(category)) {
                filterByCategory.add(car);
            }
        }
        int choiceCar = (int) (Math.random() * filterByCategory.size());
        return filterByCategory.get(choiceCar);
    }
}