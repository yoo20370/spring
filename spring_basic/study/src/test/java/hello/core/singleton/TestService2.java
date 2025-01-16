package hello.core.singleton;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestService2 {

    public HashMap<String, Object> order(String name, int price) {
        HashMap<String, Object> returnData = new HashMap<>();
        returnData.put("name", name);
        returnData.put("price", price);
        return returnData;
    }

}
