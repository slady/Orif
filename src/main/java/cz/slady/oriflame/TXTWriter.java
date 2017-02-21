package cz.slady.oriflame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class TXTWriter {

    final BufferedWriter writer;

    public TXTWriter(final String txtName, final List<Item> itemList) throws IOException {
        writer = new BufferedWriter(new FileWriter(txtName));
        write(itemList.size());

        for (final Item item : itemList) {
            writeItem(item);
        }

        this.writer.close();
    }

    private void writeItem(final Item item) throws IOException {
        write(item.getCode());
        write(item.getName().replace("\n", ""));
        write(item.getPriceBuy());
        write(item.getPriceSell());
    }

    private void write(final int number) throws IOException {
        writer.write(Integer.toString(number));
        writer.newLine();
    }

    private void write(final double number) throws IOException {
        writer.write(Double.toString(number));
        writer.newLine();
    }

    private void write(final String text) throws IOException {
        writer.write(text);
        writer.newLine();
    }

}
