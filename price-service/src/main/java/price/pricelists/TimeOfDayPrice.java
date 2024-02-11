package price.pricelists;

import lombok.Getter;

@Getter
public class TimeOfDayPrice {
    private double morningPrice = 30;
    private double dayPrice = 15;
    private double eveningPrice = 35;
    private double nightPrice = 50;
}
