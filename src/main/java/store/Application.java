package store;

import camp.nextstep.edu.missionutils.Console;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import javax.lang.model.element.QualifiedNameable;
import javax.swing.plaf.BorderUIResource;
import org.junit.jupiter.api.Order;
import store.View.OutputView;
import store.domain.Promotions;
import store.domain.Receipt;
import store.domain.Stock;
import store.domain.Promotion;
import store.domain.Stocks;

public class Application {

    public static void main(String[] args) {
        // TODO: 프로그램 구현

        // 입력 받기 전 파일 입력 단계
        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\";
        System.out.println(path);
        LocalDate today = LocalDate.now();
        List<Stock> stocks = new ArrayList<>();
//        List<Promotion> promotions = new ArrayList<>();
        HashMap<String, Promotion> promotions = new HashMap<>();

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

        try (BufferedReader br = new BufferedReader(new FileReader(path + "promotions.md"))) {
            br.readLine();
            while (true) {
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
                Promotion promotion = new Promotion(name, buy, get, startDate, endDate);
                promotions.put(name, promotion);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        boolean isContinue = true;
        OutputView output = new OutputView();
        while (isContinue) {
            output.printStatus(stocks);
            System.out.println("구매하실 상품명과 수량을 입력해주세요. (예 : [사이다-2],[감자칩-1]");
            String input = Console.readLine();
            String[] menu = input.split(",");
            List<Receipt> receipts = new ArrayList<>();
            // 입력 받은 상품으로 검색
            for (String product : menu) {
                String orderName = product.split("-")[0];
                boolean exists = stocks.stream()
                        .anyMatch(stock -> stock.getName().equals(orderName));
                if (!exists) {
                    throw new IllegalArgumentException("상품 이름을 정확하게 입력해주세요");
                }
                System.out.println(orderName);
                int orderQuantity = Integer.parseInt(product.split("-")[1]);
                System.out.println(orderQuantity);

                // 이름으로 일단 검색
                List<Stock> searchResult = stocks.stream()
                        .filter(stock -> stock.getName().equals(orderName))
                        .sorted(Comparator.comparing(
                                stock -> stock.getPromotion() == null || "null".equals(
                                        stock.getPromotion())
                        ))
                        .toList();

                //프로모션인지 확인
                for (Stock stock : searchResult) {
                    //일단 재고 확인부터
                    //전부 재고 확인했는데 구매 불가능하면 out 하고 에러 메시지
                    if(stock.getQuantity() < orderQuantity) {
                        continue;
                    }
                    //프로모션일 경우
                    int stockQuantity = stock.getQuantity();
                    if (!"null".equals(stock.getPromotion())) {
                        Promotion targetPromotion = promotions.get(stock.getPromotion());
                        //프로모션 기간 해당 확인
                        if (today.isAfter(targetPromotion.getStartDate()) && today.isBefore(
                                targetPromotion.getEndDate())) {
                            int buy = targetPromotion.getBuy();
                            int get = targetPromotion.getGet();
                            // 최소한의 증정 상품 증정 가능 상황이 아닌 경우
                            if(stockQuantity <(buy + get)){
                                //일반 상품으로 가던가 break;
                                break;
                            }
                            int extraQuantity = 0;
                            if(orderQuantity % (buy + get) != 0 ) {
                                int temp = orderQuantity / (buy + get);
                                extraQuantity += temp;
                                System.out.println("현재 {" + stock.getName() + "}은(는) " + get + "개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");
                                Character agree = Console.readLine().toUpperCase().charAt(0);
                                if(agree == 'Y') { // 추가분 주문에 추가
                                    orderQuantity = orderQuantity  + ((buy + get) - orderQuantity % (buy + get));
                                    extraQuantity += 1;
                                }else{ // 추가분 주문에서 제외
                                    orderQuantity = orderQuantity - orderQuantity % (buy + get);
                                }
                            }
                            stock.updateQuantity(orderQuantity);
                            Receipt order = new Receipt(orderName, orderQuantity,extraQuantity,stock.getPrice());
                            receipts.add(order);
                        }
                    //프로모션 아닌 경우
                    } else {
                        stock.updateQuantity(orderQuantity);
                        // 주문 수량 orderQuantity
                        // 가격 price
                        Receipt order = new Receipt(orderName, orderQuantity, 0, stock.getPrice());
                        receipts.add(order);
                    }
                }

            }
            output.printReceipt(receipts);
            isContinue = false;
        }

    }
}
