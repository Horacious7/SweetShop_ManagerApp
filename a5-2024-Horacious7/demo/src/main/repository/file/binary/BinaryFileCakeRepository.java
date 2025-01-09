package main.repository.file.binary;

import main.domain.BirthdayCake;

public class BinaryFileCakeRepository<Integer> extends BinaryFileRepository<Integer, BirthdayCake<Integer>> {
  public BinaryFileCakeRepository(String filename) {
    super(filename);
  }
}
