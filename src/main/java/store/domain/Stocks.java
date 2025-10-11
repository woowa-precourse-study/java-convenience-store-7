package store.domain;

import java.util.List;

public class Stocks {
    private List<Stock> stocks;
    public Stocks() {}

    public void addStock(Stock stock) {
        stocks.add(stock);
    }
}
