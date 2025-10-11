package store.View;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    public void startOrder(){
        System.out.println("구매하실 상품명과 수량을 입력해주세요. (예 : [사이다-2],[감자칩-1]");
        String order = Console.readLine();
        String[] orders = order.split(",");

    }

    public void continueOrder(){

    }
}
