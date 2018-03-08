package note3.core;

import note3.*;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Vector;

/**
 * Created by kanyuxia on 2017/5/5.
 */
public class SimplePipeline implements Pipeline, ValveContext, Contained, Lifecycle {
    private Container container;
    private Valve basic;
    private Vector<Valve> valves = new Vector<>();
    private LifecycleSupport lifecycleSupport = new LifecycleSupport(this);
    private boolean started = false;

    SimplePipeline(Container container) {
        this.container = container;
    }


    // --------------------------implements ValveContext interface methods
    @Override
    public void invokeNext(Request request, Response response) throws IOException, ServletException {
        int index = 0;
        if (index < valves.size()) {
            index++;
            valves.get(index).invoke(request, response, this);
        } else if (index == valves.size() && basic != null) {
            basic.invoke(request, response, this);
        } else {
            throw new ServletException();
        }
    }

    // --------------------------implements Pipeline interface methods
    @Override
    public Valve getBasic() {
        return basic;
    }

    @Override
    public void setBasic(Valve valve) {
        this.basic = valve;
    }

    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {
        invokeNext(request, response);
    }

    @Override
    public void addValve(Valve valve) {
        valves.add(valve);
    }

    @Override
    public Valve[] getValves() {
        Valve[] valveArray = new Valve[valves.size()];
        valveArray = valves.toArray(valveArray);
        return valveArray;
    }

    @Override
    public void removeValve(Valve valve) {
        valves.remove(valve);
    }

    // ---------------------implements Lifecycle interface method
    @Override
    public void addLifecycleListener(LifecycleListener listener) {
        lifecycleSupport.addLifecycleListener(listener);
    }

    @Override
    public void removeLifecycleListener(LifecycleListener listener) {
        lifecycleSupport.removeLifecycleListener(listener);
    }

    @Override
    public void start() {
        if (started) {
            return;
        }

        // 通知监听器
        lifecycleSupport.fireLifecycleListener(BEFORE_START_EVENT, null);
        started = true;

        // 通知所有的Valve
        for (Valve valve : valves) {
            if (valve instanceof Lifecycle) {
                ((Lifecycle) valve).start();
            }
        }

        // 通知basic Valve
        if (basic != null && basic instanceof Lifecycle) {
            ((Lifecycle) basic).start();
        }

        // 通知监听器
        lifecycleSupport.fireLifecycleListener(START_EVENT, null);
    }

    @Override
    public void stop() {
        if (!started) {
            return;
        }

        // 通知监听器
        lifecycleSupport.fireLifecycleListener(BEFORE_STOP_EVENT, null);
        lifecycleSupport.fireLifecycleListener(STOP_EVENT, null);
        started = false;

        // 通知basic Valve
        if (basic != null && basic instanceof Lifecycle) {
            ((Lifecycle) basic).stop();
        }

        // 通知所有的Valve
        for (Valve valve : valves) {
            if (valve instanceof Lifecycle) {
                ((Lifecycle) valve).stop();
            }
        }
    }

    // ---------------------------implements Contained interfce method

    @Override
    public Container getContainer() {
        return container;
    }

    @Override
    public void setContainer(Container container) {
        this.container = container;
    }
}
