package note2;

/**
 * Created by kanyuxia on 2017/5/3.
 */
public class BootStrap {
    public static void main(String[] args) {
        HttpConnector httpConnector = new HttpConnector();
        httpConnector.setContainer(new SimpleContainer());
        httpConnector.initialize();
        httpConnector.start();
    }
}
