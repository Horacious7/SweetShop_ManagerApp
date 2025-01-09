package main.filter.CakeFilters;

import main.domain.BirthdayCake;
import main.filter.AbstractFilter;

public class FilterCakeByPrice<ID> implements AbstractFilter<BirthdayCake<ID>> {
    private double minPrice;
    private double maxPrice;

    public FilterCakeByPrice(double minPrice, double maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    @Override
    public boolean apply(BirthdayCake<ID> cake) {
        return cake.getPrice() >= minPrice && cake.getPrice() <= maxPrice;
    }
}