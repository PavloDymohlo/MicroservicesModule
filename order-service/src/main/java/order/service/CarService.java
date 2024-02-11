package order.service;

import order.model.Car;
import order.model.CarCategory;

import java.util.ArrayList;
import java.util.List;

public class CarService {
    private static final CarService INSTANCE = new CarService();

    public static CarService getInstance() {
        return INSTANCE;
    }

    private List<Car> cars;

    private CarService() {
        cars = new ArrayList<>();
        cars.add(new Car(CarCategory.ECONOMY, "Daewoo Lanos"));
        cars.add(new Car(CarCategory.ECONOMY, "Kia Ceed"));
        cars.add(new Car(CarCategory.ECONOMY, "Opel Vectra"));

        cars.add(new Car(CarCategory.STANDARD, "Kia Rio"));
        cars.add(new Car(CarCategory.STANDARD, "Volkswagen Passat"));
        cars.add(new Car(CarCategory.STANDARD, "Opel Astra"));

        cars.add(new Car(CarCategory.COMFORT, "Skoda Superb"));
        cars.add(new Car(CarCategory.COMFORT, "BMW X5"));
        cars.add(new Car(CarCategory.COMFORT, "Infiniti Q30"));
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