package main.service;

import main.domain.BirthdayCake;
import main.filter.CakeFilters.FilterCakeByFlavour;
import main.filter.CakeFilters.FilterCakeByPrice;
import main.repository.IRepository;
import main.repository.memory.InMemoryFilteredRepository;
import main.validators.CakeValidator;

import java.util.List;

public class BirthdayCakeService<ID> {
    private IRepository<ID, BirthdayCake<ID>> cakeRepository;
    private CakeValidator<ID> cakeValidator;
    private Integer currentCakeId;

    public BirthdayCakeService(IRepository<ID, BirthdayCake<ID>> repository) {
        this.cakeRepository = repository;
        this.currentCakeId = ((List<BirthdayCake<ID>>) cakeRepository.getAll()).size() + 1;
        this.cakeValidator = new CakeValidator<>();
    }

    public ID addCake(BirthdayCake<ID> cake) {
        cakeValidator.validate(cake);
        cake.setId((ID) currentCakeId);
        cakeRepository.add(cake);
        currentCakeId++;
        return cake.getId();
    }

    public int generateNewId() {
        List<BirthdayCake<ID>> cakes = (List<BirthdayCake<ID>>) getAllCakes();
        if (cakes.isEmpty()) {
            return 1;
        }
        for (int i = 1; i <= cakes.size() + 1; i++) {
            boolean idExists = false;
            for (BirthdayCake<ID> cake : cakes) {
                if (cake.getId().equals(i)) {
                    idExists = true;
                    break;
                }
            }
            if (!idExists) {
                return i;
            }
        }
        return cakes.size() + 1;
    }

    public Iterable<BirthdayCake<ID>> getAllCakes() {
        return cakeRepository.getAll();
    }

    public void updateCake(BirthdayCake<ID> cake) {
        cakeValidator.validate(cake);
        cakeRepository.update(cake.getId(), cake);
    }

    public void deleteCake(ID idToDelete) {
        boolean cakeExists = false;
        for (BirthdayCake<ID> cake : cakeRepository.getAll()) {
            if (cake.getId().equals(idToDelete)) {
                cakeExists = true;
                break;
            }
        }
        if (!cakeExists) {
            throw new IllegalArgumentException("Cake with ID " + idToDelete + " does not exist.");
        }
        cakeRepository.delete(idToDelete);
    }

    public Iterable<BirthdayCake<ID>> getCakesByFlavour(String filterFlavour) {
        FilterCakeByFlavour<ID> flavourFilter = new FilterCakeByFlavour<>(filterFlavour);
        InMemoryFilteredRepository<ID, BirthdayCake<ID>> filteredRepository = new InMemoryFilteredRepository<>(cakeRepository, flavourFilter);
        return filteredRepository.getAll();
    }

    public Iterable<BirthdayCake<ID>> getCakesByPriceRange(double minPrice, double maxPrice) {
        if (minPrice < 0 || maxPrice < 0) {
            throw new IllegalArgumentException("Prices cannot be negative!");
        }
        if (minPrice > maxPrice) {
            throw new IllegalArgumentException("Min price cannot be greater than max price!");
        }
        FilterCakeByPrice<ID> priceFilter = new FilterCakeByPrice<>(minPrice, maxPrice);
        InMemoryFilteredRepository<ID, BirthdayCake<ID>> filteredRepository = new InMemoryFilteredRepository<>(cakeRepository, priceFilter);
        return filteredRepository.getAll();
    }
}