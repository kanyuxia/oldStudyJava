package test;

/**
 * Created by kanyuxia on 2017/5/3.
 */
public class Test {
    public static void main(String[] args) {
        Writer writer = new Writer("看见");
        ReaderA readerA = new ReaderA();
        ReaderB readerB = new ReaderB();
        writer.addObserver(readerA);
        writer.addObserver(readerB);
        writer.write();
    }
}
