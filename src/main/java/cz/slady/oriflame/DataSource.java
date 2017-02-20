package cz.slady.oriflame;

import cz.slady.oriflame.Item;
import cz.slady.oriflame.ItemSource;
import cz.slady.oriflame.TXTReader;
import cz.slady.oriflame.TXTWriter;
import cz.slady.oriflame.XLSReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class DataSource {
    final List<ItemSource> readers = new ArrayList();

    public DataSource() throws InvalidFormatException, IOException {
        prepareTxtDir();

        final File[] files = (new File("xls")).listFiles();
        final int len = files.length;

        for (int i = 0; i < len; ++i) {
            final File file = files[i];
            final String xmlName = file.getName();
            final int length = xmlName.length();
            if (length < 4 || !xmlName.toLowerCase().endsWith(".xls")) {
                throw new IllegalStateException("unexpected file name: " + xmlName);
            }

            final String txtName = "txt/" + xmlName.substring(0, length - 4) + ".txt";
            final File txtFile = new File(txtName);

            if (txtFile.exists()) {
                readers.add(new TXTReader(txtFile));
            } else {
                final XLSReader xlsReader = new XLSReader(file);
                readers.add(xlsReader);
                new TXTWriter(txtName, xlsReader.getItemList());
            }
        }

    }

    private void prepareTxtDir() {
        final File f = new File("txt");
        if (!f.exists()) {
            f.mkdir();
        }

        if (!f.isDirectory()) {
            throw new IllegalStateException("directory 'txt' not ready");
        }
    }

    public List<Item> getItemsById(final String code) {
        final ArrayList items = new ArrayList();

        for (final ItemSource reader : readers) {
            final Item item = reader.getItemById(code);

            if(item != null) {
                items.add(item);
            }

        }

        return items;
    }

}
