package main.filter.CakeFilters;

import main.domain.BirthdayCake;
import main.filter.AbstractFilter;

public class FilterCakeByFlavour<ID> implements AbstractFilter<BirthdayCake<ID>> {
    private String flavour;

    public FilterCakeByFlavour(String flavour) {
        this.flavour = flavour;
    }

    @Override
    public boolean apply(BirthdayCake<ID> cake) {
        return cake.getFlavour().equalsIgnoreCase(flavour);
    }
}