package store;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import store.View.OutputView;
import store.domain.Promotions;
import store.domain.Stock;
import store.domain.Promotion;
import store.domain.Stocks;

public class Application {

    public static void main(String[] args) {
        // TODO: 프로그램 구현

        // 입력 받기 전 파일 입력 단계
        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\";
        System.out.println(path);
        List<Stock> stocks = new ArrayList<>();
        List<Promotion> promotions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path + "products.md"))) {
            br.readLine();
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                String[] productInfo = line.split(",");
                String name = productInfo[0];
                int price = Integer.parseInt(productInfo[1]);
                int quantity = Integer.parseInt(productInfo[2]);
                String promotionName = productInfo[3];
                Stock stock = new Stock(name, price, quantity, promotionName);
                stocks.add(stock);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try(BufferedReader br = new BufferedReader(new FileReader(path + "promotions.md"))){
            br.readLine();
            while(true){
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                String[] promotionInfo = line.split(",");
                String name = promotionInfo[0];
                int buy = Integer.parseInt(promotionInfo[1]);
                int get = Integer.parseInt(promotionInfo[2]);
                LocalDate startDate = LocalDate.parse(promotionInfo[3]);
                LocalDate endDate = LocalDate.parse(promotionInfo[4]);
                Promotion promotion = new Promotion(name,buy,get,startDate,endDate);
                promotions.add(promotion);
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }

        OutputView output = new OutputView();
        output.printStatus(stocks);


    }
}
