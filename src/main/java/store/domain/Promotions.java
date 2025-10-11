package store.domain;

import java.util.List;

public class Promotions {
    private List<Promotion> promotions;
    public Promotions() {}

    public void addPromotion(Promotion promotion) {
        promotions.add(promotion);
    }
}
