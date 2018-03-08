package note3;

import java.util.EventListener;

/**
 * Interface defining a listener for significant events (including "component
 * start" and "component stop" generated by a component that implements the
 * Lifecycle interface.
 *
 * Created by kanyuxia on 2017/5/4.
 * 模拟org.apache.catalina.LifecycleListener接口：简化了许多的方法，方法的说明来自源码.
 */
public interface LifecycleListener extends EventListener {
    /**
     * Acknowledge the occurrence of the specified event.
     *
     * @param event LifecycleEvent that has occurred
     */
    void lifecycleEvent(LifecycleEvent event);
}
