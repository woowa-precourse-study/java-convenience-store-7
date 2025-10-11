package store.domain;

public class Receipt {
    private String name;
    private int buyQuantity;
    private int extraQuantity;
    private int price;

    public Receipt(String name, int buyQuantity, int extraQuantity, int price) {
        this.name = name;
        this.buyQuantity = buyQuantity;
        this.extraQuantity = extraQuantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }
    public int getBuyQuantity() {
        return buyQuantity;
    }

    public int getExtraQuantity() {
        return extraQuantity;
    }

    public int getPrice() {
        return price;
    }
}
