package test4;

/**
 * Created by king_ant on 2016/10/31.
 */
public class InnerClass {
    public static void main(String[] args) {
        new Outer().getParcel(123);
    }
}

class Parcel {
    Parcel(int x) {
        System.out.println("x = " + x);
    }
    int f(){return 0;}
}

class Outer {
    Parcel getParcel(int p) {
        return new Parcel(1) {
            private int z = p;
            @Override
            int f() {
               return p;
            }
        };
    }
}

