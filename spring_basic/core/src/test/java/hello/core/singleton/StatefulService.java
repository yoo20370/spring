package hello.core.singleton;

public class StatefulService {

//    public void order(String name, int price){
//        System.out.println("name = " + name + " price = " + price);
//        this.price = price; // 여기가 문제 !!
//    }

    public int order(String name, int price){
        System.out.println("name = " + name + " price = " + price);
        return price;
    }

}
