package cz.slady.oriflame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class TXTWriter {
    final BufferedWriter writer;

    public TXTWriter(String txtName, List<Item> itemList) throws IOException {
        this.writer = new BufferedWriter(new FileWriter(txtName));
        this.write(itemList.size());
        Iterator var4 = itemList.iterator();

        while(var4.hasNext()) {
            Item item = (Item)var4.next();
            this.writeItem(item);
        }

        this.writer.close();
    }

    private void writeItem(Item item) throws IOException {
        this.write(item.getCode());
        this.write(item.getName());
        this.write(item.getPriceBuy());
        this.write(item.getPriceSell());
    }

    private void write(int number) throws IOException {
        this.writer.write("" + number);
        this.writer.newLine();
    }

    private void write(double number) throws IOException {
        this.writer.write("" + number);
        this.writer.newLine();
    }

    private void write(String text) throws IOException {
        this.writer.write(text);
        this.writer.newLine();
    }
}
