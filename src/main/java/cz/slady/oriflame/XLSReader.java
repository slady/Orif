package cz.slady.oriflame;

import cz.slady.oriflame.Item;
import cz.slady.oriflame.ItemSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class XLSReader implements ItemSource {
    private static final int ROW_CODE = 0;
    private static final int ROW_NAME = 1;
    private static final int ROW_PRICE_BUY = 5;
    private static final int ROW_PRICE_SELL = 6;
    final DataFormatter formatter = new DataFormatter(true);
    final FormulaEvaluator evaluator;
    final Map<String, Item> items = new HashMap();
    final List<Item> itemList = new ArrayList();
    final String fileName;

    public XLSReader(File file) throws InvalidFormatException, IOException {
        this.fileName = file.getName();
        final FileInputStream fis = new FileInputStream(file);
        final Workbook workbook = WorkbookFactory.create(fis);
        this.evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        this.readSheet(workbook, 0);
        this.readSheet(workbook, "Speciální akce");
        this.readSheet(workbook, "speciální akce");
        this.readSheet(workbook, "Mimořádná nabídka");
        fis.close();
    }

    public Item getItemById(String code) {
        return this.items.get(code);
    }

    public List<Item> getItemList() {
        return this.itemList;
    }

    private void readSheet(final Workbook workbook, final int sheetNum) {
        final Sheet sheet = workbook.getSheetAt(sheetNum);
        readSheet(sheet);
    }

    private void readSheet(final Workbook workbook, final String sheetName) {
        final Sheet sheet = workbook.getSheet(sheetName);
        readSheet(sheet);
    }

    private void readSheet(final Sheet sheet) {
        if (sheet != null) {
            final int lastRowNum = sheet.getLastRowNum();

            for (int rowNum = 2; rowNum <= lastRowNum; ++rowNum) {
                readRow(sheet.getRow(rowNum));
            }

        }
    }

    private void readRow(final Row row) {
        if(row != null) {
            String code = this.readCellValue(row, 0);
            if(code != null && !code.isEmpty()) {
                double priceBuy;
                try {
                    priceBuy = this.readCellNum(row, 5);
                } catch (Exception var10) {
                    var10.printStackTrace();
                    System.out.println();
                    return;
                }

                String name;
                try {
                    String priceSell = this.readCellValue(row, 1);
                    name = priceSell;
                } catch (final RuntimeException e) {
                    name = "???";
                }

                double priceSell1 = this.readCellNum(row, 6);
                Item item = new Item(code, name, priceBuy, priceSell1, this.fileName);
                this.items.put(code, item);
                this.itemList.add(item);
            }
        }
    }

    private double readCellNum(Row row, int cellNum) {
        String cellValue = this.readCellValue(row, cellNum);
        return Double.valueOf(cellValue.replace(',', '.')).doubleValue();
    }

    private String readCellValue(Row row, int cellNum) {
        return this.formatter.formatCellValue(row.getCell(cellNum), this.evaluator);
    }
}
