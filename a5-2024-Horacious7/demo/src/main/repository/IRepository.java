package main.repository;

import main.domain.Identifiable;


public interface IRepository<ID, Entity extends Identifiable<ID>> {
    Iterable<Entity> getAll();
    void update(ID id, Entity updatedEntity);
    ID add(Entity entity);
    void delete(ID id);
}