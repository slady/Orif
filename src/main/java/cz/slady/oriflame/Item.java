package cz.slady.oriflame;

public class Item {
    final String code;
    final String name;
    final double priceBuy;
    final double priceSell;
    final String fileName;

    public Item(String code, String name, double priceBuy, double priceSell, String fileName) {
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

    private String numConv(double num) {
        String x = "" + num;
        return x.endsWith(".0")?x.substring(0, x.length() - 2):x.replace('.', ',');
    }
}
