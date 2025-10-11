package store.domain;

public class Stock {
    private String name;
    private int price;
    private int quantity;
    private String promotion;

    public Stock(String name, int price, int quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public String getName() {
        return name;
    }

    public int getPrice(){
        return price;
    }

    public int getQuantity(){
        return quantity;
    }

    public String getPromotion(){
        return promotion;
    }

    public void updateQuantity(int quantity){
        this.quantity -= quantity;
    }
}
