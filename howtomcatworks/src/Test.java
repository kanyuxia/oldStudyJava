/**
 * Created by kanyuxia on 2017/5/6.
 */
public class Test {
    public static void main(String[] args) {
        Sub sub = new Sub();
    }
}

class Super {
    static {
        System.out.println("Super.static.area");
    }

    private int a = 2;
    private int b = 1;

    Super() {
        System.out.println("Super() a: " + a);
        System.out.println("Super() b: " + b);
        method();
    }

    void method() {
        System.out.println("Super.method");
    }
}

class Sub extends Super {
    static {
        System.out.println("Sub.static.area");
    }

    private int b = 2;

    Sub() {
        System.out.println("Sub() b: " + b);
        method();
    }

    @Override
    void method() {
        System.out.println("b: " + b);
    }
}