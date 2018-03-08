package reflect;

/**
 * Created by kanyuxia on 2016/11/15.
 */
public class ClassDemo1 {

    public static void main(String[] args) {
        Object o1 = new Foo(1);
        Object o2 = new ClassDemo1();
        System.out.println("o1:" + o1);
        System.out.println("o2:" + o2);
        System.out.println(o1 == o2);
        Class c1 = Foo.class;
        Foo foo = new Foo(2);
        Class c2 = foo.getClass();
        System.out.println("c1:" + c1);
        System.out.println("c2:" + c2);
        System.out.println(c1 == c2);
        System.out.println();
        Class c3 = null;
        try {
            c3 = Class.forName("reflect.Foo");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(c1 == c3);

        try {
            Foo foo1 = (Foo) c1.newInstance();
            foo1.print();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

class Foo {
    void print(){
        System.out.println("foo");
    }
    Foo(int i){}
    Foo(){

    }
}
