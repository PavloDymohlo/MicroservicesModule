package price.pricelists;

import lombok.Getter;

@Getter
public class DistancePriceByTimeOfDay {
    private double morningKilometer = 3;
    private double dayKilometer = 1;
    private double eveningKilometer = 3;
    private double nightKilometer = 5;
}
