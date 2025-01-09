package main.repository.file.binary;

import main.domain.Order;

public class BinaryFileOrderRepository<Integer> extends BinaryFileRepository<Integer,Order<Integer>>{
    public BinaryFileOrderRepository(String filename) {
        super(filename);
    }
}
