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
        fileName = file.getName();
        reader = new BufferedReader(new FileReader(file));

        int lineCount = readLineInt();

        for (int i = 0; i < lineCount; ++i) {
            readItem();
        }

        this.reader.close();
    }

    private void readItem() throws IOException {
        final String code = reader.readLine();
        final String name = reader.readLine();
        final double priceBuy = readLineDouble();
        final double priceSell = readLineDouble();
        final Item item = new Item(code, name, priceBuy, priceSell, fileName);
        items.put(code, item);
    }

    private int readLineInt() throws IOException {
        return Integer.parseInt(reader.readLine());
    }

    private double readLineDouble() throws NumberFormatException, IOException {
        return Double.parseDouble(this.reader.readLine());
    }

    public Item getItemById(final String code) {
        return items.get(code);
    }

}
