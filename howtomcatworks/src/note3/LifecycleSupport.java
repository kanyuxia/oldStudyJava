package note3;

import java.util.Vector;

/**
 * Created by kanyuxia on 2017/5/4.
 * 模拟org.apache.catalina.LifecycleSupport接口：简化了许多的方法，方法的说明来自源码，并且自己改了一下。
 */
public class LifecycleSupport {
    private Vector<LifecycleListener> listeners = new Vector<>();

    private Lifecycle lifecycle;

    public LifecycleSupport(Lifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }

    public void addLifecycleListener(LifecycleListener lifecycleListener) {
        listeners.add(lifecycleListener);
    }

    public void removeLifecycleListener(LifecycleListener lifecycleListener) {
        listeners.remove(lifecycleListener);
    }

    public void fireLifecycleListener(String type, Object data) {
        LifecycleEvent event = new LifecycleEvent(lifecycle, type, data);
        for (LifecycleListener lifecycleListener : listeners) {
            lifecycleListener.lifecycleEvent(event);
        }
    }
}
