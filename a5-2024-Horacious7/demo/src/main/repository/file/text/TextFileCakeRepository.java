package main.repository.file.text;

import main.domain.BirthdayCake;

public class TextFileCakeRepository extends TextFileRepository<Integer, BirthdayCake<Integer>> {
    public TextFileCakeRepository(String filename) {
        super(filename);
        super.readFromFile();
    }

    @Override
    protected BirthdayCake<Integer> parseEntity(String line) {
        String[] tokens = line.split(",");
        Integer cakeId = Integer.parseInt(tokens[0].trim());
        String cakeName = tokens[1].trim();
        String cakeFlavour = tokens[2].trim();
        double cakePrice = Double.parseDouble(tokens[3].trim());
        return new BirthdayCake<>(cakeId, cakeName, cakeFlavour, cakePrice);
    }

}
