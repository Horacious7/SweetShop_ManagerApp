package main.repository.file.binary;

import main.domain.Identifiable;
import main.repository.file.FileRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BinaryFileRepository<ID, Entity extends Identifiable<ID>> extends FileRepository<ID, Entity> {
    public BinaryFileRepository(String filename) {
        super(filename);
        ensureFileExists();
        readFromFile();
    }

    private void ensureFileExists() {
        File file = new File(filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error creating file: " + filename, e);
        }
    }

    @Override
    protected void readFromFile() {
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(filename))) {
            List<Entity> entities = (List<Entity>) input.readObject();
            for (Entity entity : entities) {
                super.add(entity);
            }
        }catch(EOFException e){

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error reading from binary file: " + filename ,e);
        }
    }

    @Override
    protected void writeToFile() {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filename))) {
            List<Entity> entities = new ArrayList<>();
            getAll().forEach(entities::add);
            output.writeObject(entities);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + filename, e);
        }
    }
}
