package note3.core;

import note3.*;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by kanyuxia on 2017/5/5.
 */
public class SimpleWrapper implements Wrapper, Pipeline, Lifecycle {

    private LifecycleSupport lifecycleSupport = new LifecycleSupport(this);
    private Loader loader;
    private String name;
    private Servlet instance;
    private String servletClass;
    private Pipeline pipeline = new SimplePipeline(this);
    private Container parent;
    private boolean started = false;

    public SimpleWrapper() {
        pipeline.setBasic(new SimpleWrapperValve(this));
    }


    // ----------------------------implements Wrapper interface methods
    @Override
    public String getServletClass() {
        return servletClass;
    }

    @Override
    public void setServletClass(String servletClass) {
        this.servletClass = servletClass;
    }

    @Override
    public Servlet allocate() throws ServletException {
        // Load and initialize our instance if necessary
        if (instance == null) {
            try {
                loadServlet();
            } catch (ServletException e) {
                throw e;
            } catch (Throwable e) {
                throw new ServletException(e);
            }
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    private void loadServlet() throws ServletException {
        if (instance != null) {
            return;
        }
        String actualClass = servletClass;
        if ((actualClass == null) ) {
            throw new ServletException("");
        }
        Loader loader = getLoader();
        if (loader == null) {
            throw new ServletException("");
        }
        ClassLoader classLoader = loader.getClassLoader();

        Class<Servlet> servletClass = null;
        try {
            if (classLoader!=null) {
                servletClass = (Class<Servlet>) classLoader.loadClass(actualClass);
            }
        }
        catch (ClassNotFoundException e) {
            throw new ServletException("");
        }

        try {
            instance = servletClass.newInstance();
        } catch (Throwable e) {
            throw new ServletException("");
        }

        // Call the initialization method of this servlet
        try {
            instance.init(null);
        } catch (Throwable f) {
            throw new ServletException("Failed initialize servlet.");
        }
    }

    // --------------------------------implements Pipeline interface methods
    @Override
    public Valve getBasic() {
        return pipeline.getBasic();
    }

    @Override
    public void setBasic(Valve valve) {
        pipeline.setBasic(valve);
    }

    @Override
    public synchronized void addValve(Valve valve) {
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

    // ------------------------implements Lifecycle interface methods
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
        // 启动组件
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
    public void stop() {
        // Shut down our servlet instance (if it has been initialized)
        instance.destroy();

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

    // ----------------------------implements Container interface methods
    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {
        pipeline.invoke(request, response);
    }

    @Override
    public Loader getLoader() {
        if (loader != null) {
            return loader;
        }
        if (parent != null) {
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
            return parent.getLoader().getClassLoader();
        }
        return null;
    }

    @Override
    public void setParentClassLoader(ClassLoader parent) {
    }

    @Override
    public void addChild(Container child) {

    }

    @Override
    public void removeChild(Container child) {

    }

    @Override
    public Container findChild(String name) {
        return null;
    }

    @Override
    public Container[] findChildren() {
        return new Container[0];
    }

    @Override
    public Container map(Request request, boolean update) {
        return null;
    }

    @Override
    public void addMapper(Mapper mapper) {

    }

    @Override
    public void removeMapper(Mapper mapper) {

    }

    @Override
    public Mapper findMapper(String protocol) {
        return null;
    }

    @Override
    public Mapper[] findMappers() {
        return new Mapper[0];
    }
}
