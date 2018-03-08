package test;

/**
 * Created by kanyuxia on 2017/5/4.
 */
public class GUI {
    public static void main(String[] args) {
        Button button = new Button();
        SoundListener soundListener = new SoundListener();
        MovieListener movieListener = new MovieListener();
        button.addListener(soundListener);
        button.addListener(movieListener);
        button.onclick();
    }
}
