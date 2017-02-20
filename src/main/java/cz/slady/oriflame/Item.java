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
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public double getPriceBuy() {
        return this.priceBuy;
    }

    public double getPriceSell() {
        return this.priceSell;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String toString() {
        return this.getFileName() + "\t" + this.getCode() + "\t" + this.getName() + "\t" + this.numConv(this.priceBuy) + "\t" + this.numConv(this.priceSell);
    }

    private String numConv(final double num) {
        final String d = "" + num;
        return d.endsWith(".0") ? d.substring(0, d.length() - 2) : d.replace('.', ',');
    }
}
