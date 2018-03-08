package test4.test2;

import test4.test1.Function;

/**
 * Created by king_ant on 2016/10/31.
 */
public class Outer {
    protected class Inner implements Function {
        public Inner(){

        }
        @Override
        public void sayHello() {
            System.out.println("I am Outer.Inner.sayHello()");
        }
    }
}
