package hello.core.singleton;

public class TestService {

    private String userName;
    private int price = 0;


    public void order(String name, int price) {
        this.userName = name;
        this.price = price;
    }

    public int getPrice(){
        return price;
    }
}
