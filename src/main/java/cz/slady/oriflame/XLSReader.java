package cz.slady.oriflame;

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

    public XLSReader(final File file) throws InvalidFormatException, IOException {
        this.fileName = file.getName();
        final FileInputStream fis = new FileInputStream(file);
        final Workbook workbook = WorkbookFactory.create(fis);
        this.evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        readSheet(workbook, 0);
        readSheet(workbook, "Speciální akce");
        readSheet(workbook, "speciální akce");
        readSheet(workbook, "Mimořádná nabídka");
        fis.close();
    }

    public Item getItemById(String code) {
        return items.get(code);
    }

    public List<Item> getItemList() {
        return itemList;
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
        if (row == null) {
            return;
        }

        final String code = readCellValue(row, ROW_CODE);
        if (code == null || code.isEmpty()) {
            return;
        }

        final double priceBuy;

        try {
            priceBuy = readCellNum(row, ROW_PRICE_BUY);
        } catch (final Exception ex) {
            ex.printStackTrace();
            System.out.println();
            return;
        }

        String name;
        try {
            name = readCellValue(row, ROW_NAME);
        } catch (final RuntimeException e) {
            name = "???";
        }

        final double priceSell = readCellNum(row, ROW_PRICE_SELL);
        final Item item = new Item(code, name, priceBuy, priceSell, fileName);
        this.items.put(code, item);
        this.itemList.add(item);
    }

    private double readCellNum(final Row row, final int cellNum) {
        final String cellValue = readCellValue(row, cellNum);
        return Double.parseDouble(cellValue.replace(" ", "").replace(',', '.'));
    }

    private String readCellValue(final Row row, final int cellNum) {
        return formatter.formatCellValue(row.getCell(cellNum), evaluator);
    }

}
