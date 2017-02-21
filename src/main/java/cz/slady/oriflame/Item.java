package cz.slady.oriflame;

public class Item {
    final String code;
    final String name;
    final double priceBuy;
    final double priceSell;
    final String fileName;

    public Item(final String code, final String name, final double priceBuy, final double priceSell, final String fileName) {
        this.code = code;
        this.name = name;
        this.priceBuy = priceBuy;
        this.priceSell = priceSell;
        this.fileName = fileName;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public double getPriceBuy() {
        return priceBuy;
    }

    public double getPriceSell() {
        return priceSell;
    }

    public String getFileName() {
        return fileName;
    }

    public String toString() {
        return getFileName() + "\t" + getCode() + "\t" + getName() + "\t" + numConv(priceBuy) + "\t" + numConv(priceSell);
    }

    private String numConv(final double num) {
        final String d = "" + num;
        return d.endsWith(".0") ? d.substring(0, d.length() - 2) : d.replace('.', ',');
    }

}
