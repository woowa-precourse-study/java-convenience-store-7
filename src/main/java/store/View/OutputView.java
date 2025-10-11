package store.View;

import java.util.List;
import store.domain.Receipt;
import store.domain.Stock;

public class OutputView {

    public void printStatus(List<Stock> stockList){
        System.out.println("안녕하세요, W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.");
        System.out.println();

        for(Stock stock : stockList){
            System.out.print("- " + stock.getName() + " " + stock.getPrice() + "원 " + stock.getQuantity()+"개 ");
            if(!"null".equals(stock.getPromotion())){
                System.out.print(stock.getPromotion());
            }
            if(stock.getQuantity()<=0){
                System.out.print(" 재고 없음");
            }
            System.out.println();
        }
    }

    public void printReceipt(List<Receipt> receipts){
        int totalQuantity = 0;
        int totalPrice = 0;
        int totalReduce = 0;
        int membershipReduce = 0;
        int finalPrice = 0;
        System.out.println("===========W 편의점===========");
        System.out.printf("상품명 %10s %10s %n","편의점","금액");
        for(Receipt receipt : receipts){
            System.out.printf("%s %10d %10d %n",receipt.getName(),receipt.getBuyQuantity(),receipt.getPrice() * receipt.getBuyQuantity());
            totalQuantity += receipt.getBuyQuantity();
            totalPrice += receipt.getPrice() * receipt.getBuyQuantity();
        }
        System.out.println("===========증   정============");
        for(Receipt receipt : receipts){
            if(receipt.getExtraQuantity()>0){
                System.out.printf("%s %10d %n",receipt.getName(),receipt.getExtraQuantity());
                totalReduce += receipt.getExtraQuantity() * receipt.getPrice();
            }
        }
        membershipReduce = (totalPrice-totalReduce) * 3 / 10;
        finalPrice = totalPrice - totalReduce - membershipReduce;
        System.out.println("=============================");
        System.out.printf("총구매액 %10d %10d %n",totalQuantity,totalPrice);
        System.out.printf("행사할인 %20d %n",totalReduce);
        System.out.printf("멤버십할인 %20d %n",membershipReduce);
        System.out.printf("내실돈 %20d %n",finalPrice);
    }

}
