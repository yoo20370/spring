package hello.core.singleton;

public class SingletonService {

    // SingletonService 클래스가 로드될 때 자기 자신의 인스턴스를 딱 한 번 생성하여, 이후 변경 불가능한 클래스 변수로 참조하게 함
    private static final SingletonService instance = new SingletonService();

    // instance 참조 변수의 Getter
    // 해당 인스턴스를 꺼낼 수 있는 방법은 이 Getter 밖에 없다.
    public static SingletonService getInstance() {
        return instance;
    }

    // private 생성자
    // 접근 제어자를 private으로 줌으로써 클래스 내부에서만 생성자를 호출할 수 있도록 한다.
    private SingletonService() {

    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
