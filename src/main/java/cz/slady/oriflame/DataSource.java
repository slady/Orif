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
        this.prepareTxtDir();
        File[] var4;
        int var3 = (var4 = (new File("xls")).listFiles()).length;

        for(int var2 = 0; var2 < var3; ++var2) {
            File file = var4[var2];
            String xmlName = file.getName();
            int length = xmlName.length();
            if(length < 4 || !xmlName.toLowerCase().endsWith(".xls")) {
                throw new IllegalStateException("unexpected file name: " + xmlName);
            }

            String txtName = "txt/" + xmlName.substring(0, length - 4) + ".txt";
            File txtFile = new File(txtName);
            if(txtFile.exists()) {
                this.readers.add(new TXTReader(txtFile));
            } else {
                XLSReader xlsReader = new XLSReader(file);
                this.readers.add(xlsReader);
                new TXTWriter(txtName, xlsReader.getItemList());
            }
        }

    }

    private void prepareTxtDir() {
        File f = new File("txt");
        if(!f.exists()) {
            f.mkdir();
        }

        if(!f.isDirectory()) {
            throw new IllegalStateException("directory \'txt\' not ready");
        }
    }

    public List<Item> getItemsById(String code) {
        ArrayList items = new ArrayList();
        Iterator var4 = this.readers.iterator();

        while(var4.hasNext()) {
            ItemSource reader = (ItemSource)var4.next();
            Item item = reader.getItemById(code);
            if(item != null) {
                items.add(item);
            }
        }

        return items;
    }
}
