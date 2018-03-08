package test;

/**
 * Created by kanyuxia on 2017/5/4.
 */
public class SoundListener implements OnclickListener {
    @Override
    public void onclick(ButtonEvent buttonEvent) {
        System.out.println("SoundListener listener: " + buttonEvent.getSource());
    }
}
