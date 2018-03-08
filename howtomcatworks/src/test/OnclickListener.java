package test;

import java.util.EventListener;

/**
 * Created by kanyuxia on 2017/5/3.
 * 事件监听器
 */
public interface OnclickListener extends EventListener {
    void onclick(ButtonEvent buttonEvent);
}
