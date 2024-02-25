package order.service;

import order.model.Car;
import order.model.CarCategory;

import java.util.List;

public final class CarServiceUtil {
    private CarServiceUtil() {
        throw new IllegalArgumentException();
    }

    public static void addCarsToList(List<Car> cars) {
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
}
