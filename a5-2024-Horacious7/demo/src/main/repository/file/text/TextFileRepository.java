package main.repository.file.text;

import main.domain.Identifiable;
import main.repository.file.FileRepository;

import java.io.*;

public abstract class TextFileRepository<ID, Entity extends Identifiable<ID>> extends FileRepository<ID, Entity> {
    public TextFileRepository(String filename) {
        super(filename);
        ensureFileExists();
    }

    protected abstract Entity parseEntity(String line);

    private void ensureFileExists() {
        File file = new File(filename);
        try{
            if(!file.exists()){
                file.createNewFile();
            }
        }catch(IOException e){
            throw new RuntimeException("Error while creating file: " + filename, e);
        }
    }

    @Override
    protected void readFromFile(){
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
            String line;
            while((line = reader.readLine()) != null){
                Entity entity = parseEntity(line);
                super.add(entity);
            }
        }catch(IOException e){
            throw new RuntimeException("Error while reading file: " + filename, e);
        }
    }

    @Override
    protected void writeToFile(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
            for(Entity entity : getAll()){
                writer.write(entity.toString()+"\n");
            }
        }catch(IOException e){
            throw new RuntimeException("Error while writing to file: " + filename, e);
        }
    }
}
