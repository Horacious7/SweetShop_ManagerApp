package main.repository.memory;

import main.domain.Identifiable;
import main.repository.IRepository;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryRepository<ID, Entity extends Identifiable<ID>> implements IRepository<ID, Entity> {
    private HashMap<ID, Entity> items = new HashMap<>();

    @Override
    public ID add(Entity element){
        items.put(element.getId(), element);
        return element.getId();
    }

    @Override
    public Iterable<Entity> getAll() {
        return new ArrayList<>(items.values());
    }

    @Override
    public void update(ID id,Entity updatedItem){
        items.put(id, updatedItem);
    }

    @Override
    public void delete(ID id){
        items.remove(id);
    }

}
