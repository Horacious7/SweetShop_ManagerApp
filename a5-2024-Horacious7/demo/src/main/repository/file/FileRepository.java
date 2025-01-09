package main.repository.file;

import main.domain.Identifiable;
import main.repository.memory.InMemoryRepository;

public abstract class FileRepository<ID, Entity extends Identifiable<ID>> extends InMemoryRepository<ID, Entity> {
    protected String filename;

    public FileRepository(String filename) {
        this.filename = filename;
    }

    protected abstract void readFromFile();
    protected abstract void writeToFile();

    @Override
    public ID add(Entity entity){
        ID id = super.add(entity);
        writeToFile();
        return id;
    }

    @Override
    public void delete(ID id) {
        super.delete(id);
        writeToFile();
    }

    @Override
    public void update(ID id,Entity entity) {
        super.update(id,entity);
        writeToFile();
    }
}
