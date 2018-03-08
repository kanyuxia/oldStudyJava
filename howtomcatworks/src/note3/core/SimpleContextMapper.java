package note3.core;

import note3.Container;
import note3.Mapper;
import note3.Request;
import note3.Wrapper;

/**
 * Created by kanyuxia on 2017/5/6.
 */
public class SimpleContextMapper implements Mapper {

    private SimpleContext context;
    private String protocol;

    @Override
    public Container getContainer() {
        return context;
    }

    @Override
    public void setContainer(Container container) {
        if (!(container instanceof SimpleContext)) {
            throw new IllegalStateException("");
        }
        context = (SimpleContext) container;

    }

    @Override
    public String getProtocol() {
        return protocol;
    }

    @Override
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public Container map(Request request, boolean update) {
        // Has this request already been mapped?
        if (update && request.getWrapper() != null) {
            return request.getWrapper();
        }
        // Identify the context-relative URI to be mapped
        String requestURL = request.getRequest().getRequestURI();

        // Apply the standard request URI mapping rules from the specification
        // Match Wrapper
        Wrapper wrapper = null;
        if (requestURL != null) {
            String servletName = context.findServletMapping(requestURL);
            if (servletName != null) {
                wrapper = (Wrapper) context.findChild(servletName);
            }
        }
        request.setWrapper(wrapper);
        return wrapper;
    }
}
