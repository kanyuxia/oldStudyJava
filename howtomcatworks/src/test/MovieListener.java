package test;

/**
 * Created by kanyuxia on 2017/5/4.
 */
public class MovieListener implements OnclickListener {
    @Override
    public void onclick(ButtonEvent buttonEvent) {
        System.out.println("MovieListen listen: " + buttonEvent.getSource());
    }
}
