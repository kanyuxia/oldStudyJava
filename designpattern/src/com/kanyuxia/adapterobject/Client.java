package com.kanyuxia.adapterobject;

/**
 * Created by kanyuxia on 2017/3/25.
 */
public class Client {
    public static void main(String[] args) {
        Source1 source1 = new Source1();
        Source2 source2 = new Source2();
        Source3 source3 = new Source3();
        Target target = new Adapter(source1, source2, source3);
        target.request();
    }
}
