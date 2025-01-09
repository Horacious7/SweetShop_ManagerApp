package main.filter.OrderFilters;

import main.domain.Order;
import main.filter.AbstractFilter;

public class FilterOrderByPrice<ID> implements AbstractFilter<Order<ID>> {
    private double minPrice;
    private double maxPrice;

    public FilterOrderByPrice(double minPrice, double maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    @Override
    public boolean apply(Order<ID> entity) {
        return entity.getPrice() >= minPrice && entity.getPrice() <= maxPrice;
    }
}