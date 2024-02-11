package price.pricelists;

import lombok.Getter;

@Getter
public class CoefficientPriceByCarCategory {
    private double economyKilometer = 1;
    private double standardKilometer = 1.1;
    private double comfortKilometer = 1.2;
}
