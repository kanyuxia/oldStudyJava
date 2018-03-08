package test;

import java.util.EventObject;

/**
 * Created by kanyuxia on 2017/5/3.
 * 事件对象
 */
public class ButtonEvent extends EventObject {
    private static final long serialVersionUID = 7446643402325925295L;

    ButtonEvent(Button button) {
        super(button);
    }

    @Override
    public Button getSource() {
        return (Button) super.getSource();
    }
}
