package store.View;

import java.sql.SQLOutput;
import java.util.List;
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



}
