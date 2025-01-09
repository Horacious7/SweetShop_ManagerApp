package main.validators;

import main.domain.BirthdayCake;

public class CakeValidator <ID>{
    public void validate(BirthdayCake cake) {
        validateCake(cake);
    }

    private void validateCake(BirthdayCake cake) {
        if (cake.getName() == null || cake.getName().isEmpty()) {
            throw new IllegalArgumentException("Cake name cannot be empty!");
        }
        if (cake.getFlavour() == null || cake.getFlavour().isEmpty()) {
            throw new IllegalArgumentException("Cake flavour cannot be empty!");
        }
        if (cake.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0!");
        }
    }
}