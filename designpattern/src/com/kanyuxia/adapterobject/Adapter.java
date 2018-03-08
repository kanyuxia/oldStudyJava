package com.kanyuxia.adapterobject;

/**
 * Created by kanyuxia on 2017/3/25.
 */
public class Adapter implements Target {
    private Source1 source1;
    private Source2 source2;
    private Source3 source3;

    Adapter(Source1 source1, Source2 source2, Source3 source3) {
        this.source1 = source1;
        this.source2 = source2;
        this.source3 = source3;
    }


    @Override
    public void request() {
        source1.doSomething();
        source2.doSomething();
        source3.doSomething();
    }
}
