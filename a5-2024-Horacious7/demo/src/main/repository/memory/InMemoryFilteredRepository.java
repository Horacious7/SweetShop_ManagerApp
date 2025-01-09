package main.repository.memory;
import main.domain.Identifiable;
import main.filter.AbstractFilter;
import main.repository.IRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryFilteredRepository<ID, Entity extends Identifiable<ID>> extends InMemoryRepository<ID, Entity> {
    private IRepository<ID, Entity> repository;
    private AbstractFilter<Entity> filter;

    public InMemoryFilteredRepository(IRepository<ID, Entity> repository, AbstractFilter<Entity> filter) {
        this.repository = repository;
        this.filter = filter;
    }

    @Override
    public Iterable<Entity> getAll() {
        List<Entity> filteredItems = new ArrayList<>();
        for(Entity item : this.repository.getAll()) {
            if(filter.apply(item)) {
                filteredItems.add(item);
            }
        }
        if(filteredItems.isEmpty()) {
            throw new RuntimeException("Empty repository!");
        }
        return filteredItems;
    }
}
