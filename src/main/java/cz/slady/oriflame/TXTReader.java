package cz.slady.oriflame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TXTReader implements ItemSource {
    final String fileName;
    final BufferedReader reader;
    final Map<String, Item> items = new HashMap();

    public TXTReader(final File file) throws IOException {
        this.fileName = file.getName();
        this.reader = new BufferedReader(new FileReader(file));
        int lineCount = this.readLineInt();

        for(int i = 0; i < lineCount; ++i) {
            this.readItem();
        }

        this.reader.close();
    }

    private void readItem() throws IOException {
        final String code = this.reader.readLine();
        final String name = this.reader.readLine();
        double priceBuy = this.readLineDouble();
        double priceSell = this.readLineDouble();
        final Item item = new Item(code, name, priceBuy, priceSell, this.fileName);
        this.items.put(code, item);
    }

    private int readLineInt() throws IOException {
        return Integer.parseInt(this.reader.readLine());
    }

    private double readLineDouble() throws NumberFormatException, IOException {
        return Double.parseDouble(this.reader.readLine());
    }

    public Item getItemById(final String code) {
        return this.items.get(code);
    }
}
