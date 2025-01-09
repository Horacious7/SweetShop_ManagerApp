package main.filter.OrderFilters;

import main.domain.Order;
import main.filter.AbstractFilter;

public class FilterOrderByStatus<ID> implements AbstractFilter<Order<ID>> {
    private String status;

    public FilterOrderByStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean apply(Order<ID> entity) {
        return entity.getStatus().equalsIgnoreCase(status);
    }
}
