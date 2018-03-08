package test;

import java.util.LinkedList;

/**
 * Created by kanyuxia on 2017/5/4.
 */
public class Button {

    private final LinkedList<OnclickListener> listeners = new LinkedList<>();

    private void fireEvent() {
        ButtonEvent buttonEvent = new ButtonEvent(this);
        synchronized (listeners) {
            for (OnclickListener listener : listeners) {
                listener.onclick(buttonEvent);
            }
        }
    }

    public void addListener(OnclickListener onclickListener) {
        synchronized (listeners) {
            listeners.add(onclickListener);
        }
    }

    public void removeListener(OnclickListener onclickListener) {
        synchronized (listeners) {
            listeners.remove(onclickListener);
        }
    }

    public void onclick() {
        fireEvent();
    }

    @Override
    public String toString() {
        return "Button";
    }
}
