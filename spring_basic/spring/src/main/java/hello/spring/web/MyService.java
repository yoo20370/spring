package hello.spring.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyService {

    private final MyLogger myLogger;

    public void test(String id) {
        System.out.println(myLogger.log() + " service = " + id);
    }
}
