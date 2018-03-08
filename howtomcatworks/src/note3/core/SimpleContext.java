package note3.core;

import note3.*;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kanyuxia on 2017/5/4.
 */
public class SimpleContext implements Context, Pipeline, Lifecycle {
    // Loader组件
    private Loader loader = new SimpleLoader();
    private Pipeline pipeline = new SimplePipeline(this);
    // 有关Listener的支持
    private LifecycleSupport lifecycleSupport = new LifecycleSupport(this);
    // 存放子容器
    private final Map<String, Container> children = new HashMap<>();
    // 存放Mapper
    private final Map<String, Mapper> mappers = new HashMap<>();
    // 存放servletMapping
    private final Map<String, String> servletMappings = new HashMap<>();
    // 父容器
    private Container parent;
    // 是否启动
    private boolean started = false;
    private String name;

    public SimpleContext() {
        pipeline.setBasic(new SimpleContextValve(this));
    }

    // ------------------implements Lifecycle interface methods
    @Override
    public void addLifecycleListener(LifecycleListener listener) {
        lifecycleSupport.addLifecycleListener(listener);
    }

    @Override
    public void removeLifecycleListener(LifecycleListener listener) {
        lifecycleSupport.removeLifecycleListener(listener);
    }

    @Override
    public synchronized void start() {
        if (started) {
            return;
        }
        // 通知监听器
        lifecycleSupport.fireLifecycleListener(BEFORE_START_EVENT, null);
        started = true;

        // 启动容器组件
        if (loader != null && loader instanceof Lifecycle) {
            ((Lifecycle) loader).start();
        }

        // 启动子容器
        Container[] containers = findChildren();
        for (Container container : containers) {
            if (container != null && container instanceof Lifecycle) {
                ((Lifecycle) container).start();
            }
        }

        // Start the Valves in our pipeline (including the basic)
        if (pipeline != null && pipeline instanceof Lifecycle) {
            ((Lifecycle) pipeline).start();
        }

        // 通知监听器
        lifecycleSupport.fireLifecycleListener(START_EVENT, null);

        // Notify our interested LifecycleListeners
        lifecycleSupport.fireLifecycleListener(AFTER_START_EVENT, null);
    }

    @Override
    public synchronized void stop() {
        if (!started) {
            return;
        }

        // 通知监听器
        lifecycleSupport.fireLifecycleListener(BEFORE_STOP_EVENT, null);
        started = false;

        // Notify our interested LifecycleListeners
        lifecycleSupport.fireLifecycleListener(STOP_EVENT, null);

        // Stop the Valves in our pipeline (including the basic), if any
        if (pipeline != null && pipeline instanceof Lifecycle) {
            ((Lifecycle) pipeline).stop();
        }

        // 关闭子容器
        Container[] containers = findChildren();
        for (Container container : containers) {
            if (container != null && container instanceof Lifecycle) {
                ((Lifecycle) container).stop();
            }
        }

        // 关闭组件
        if (loader != null && loader instanceof Lifecycle) {
            ((Lifecycle) loader).stop();
        }

    }


    // ------------------implements Pipeline interface methods

    @Override
    public Valve getBasic() {
        return pipeline.getBasic();
    }

    @Override
    public void setBasic(Valve valve) {
        pipeline.setBasic(valve);
    }

    @Override
    public void addValve(Valve valve) {
        pipeline.addValve(valve);
    }

    @Override
    public Valve[] getValves() {
        return pipeline.getValves();
    }

    @Override
    public void removeValve(Valve valve) {
        pipeline.removeValve(valve);
    }


    // ------------------implements Context interface methods
    @Override
    public void addServletMapping(String pattern, String name) {
        synchronized (servletMappings) {
            servletMappings.put(pattern, name);
        }
    }

    @Override
    public String findServletMapping(String pattern) {
        synchronized (servletMappings) {
            return servletMappings.get(pattern);
        }
    }

    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {
        pipeline.invoke(request, response);
    }

    @Override
    public Loader getLoader() {
        if (loader != null) {
            return loader;
        }
        if (parent !=null) {
            return parent.getLoader();
        }
        return null;
    }

    @Override
    public void setLoader(Loader loader) {
        this.loader = loader;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Container getParent() {
        return parent;
    }

    @Override
    public void setParent(Container container) {
        this.parent = container;
    }

    @Override
    public ClassLoader getParentClassLoader() {
        if (parent != null) {
            parent.getLoader().getClassLoader();
        }
        return null;
    }

    @Override
    public void setParentClassLoader(ClassLoader parent) {

    }

    @Override
    public void addChild(Container child) {
        child.setParent(this);
        synchronized (children) {
            children.put(child.getName(), child);
        }
    }

    @Override
    public void removeChild(Container child) {
        synchronized (children) {
            children.remove(child.getName(), child);
        }
    }

    @Override
    public Container findChild(String name) {
        synchronized (children) {
            return children.get(name);
        }
    }

    @Override
    public Container[] findChildren() {
        Container[] containers = new Container[children.size()];
        synchronized (children) {
            containers = children.values().toArray(containers);
        }
        return containers;
    }

    @Override
    public Container map(Request request, boolean update) {
        Mapper mapper = findMapper(request.getRequest().getProtocol());
        if (mapper == null) {
            return null;
        }
        return mapper.map(request, update);
    }

    @Override
    public void addMapper(Mapper mapper) {
        mapper.setContainer(this);
        synchronized (mappers) {
            if (mappers.get(mapper.getProtocol()) != null)
                throw new IllegalArgumentException("addMapper:  Protocol '" +
                        mapper.getProtocol() + "' is not unique");
            mappers.put(mapper.getProtocol(), mapper);
        }
    }

    @Override
    public void removeMapper(Mapper mapper) {
        synchronized (mappers) {
            mappers.remove(mapper.getProtocol(), mapper);
        }
    }

    @Override
    public Mapper findMapper(String protocol) {
        synchronized (mappers) {
            return mappers.get(protocol);
        }
    }

    @Override
    public Mapper[] findMappers() {
        Mapper[] mapperArray = new Mapper[mappers.size()];
        synchronized (mappers) {
            return mappers.values().toArray(mapperArray);
        }
    }
}
