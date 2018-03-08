package test4.test3;

import test4.test1.Function;
import test4.test2.Outer;

/**
 * Created by king_ant on 2016/10/31.
 */
public class ExtendsOuter extends Outer{
    public void getFunction(){
        Function function = new Inner();
        function.sayHello();
    }
}
