package note3.core;

import note3.*;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by kanyuxia on 2017/5/6.
 */
public class SimpleContextValve implements Valve, Contained {
    private Container container;

    SimpleContextValve (Container container) {
        this.container = container;
    }

    @Override
    public void invoke(Request request, Response response, ValveContext valveContext) throws IOException, ServletException {
        Context context = (Context) container;
        Wrapper wrapper = (Wrapper) context.map(request, true);
        if (wrapper == null) {
            return;
        }
        // Ask this Wrapper to process this Request
        response.setContext(context);
        wrapper.invoke(request, response);
    }

    @Override
    public Container getContainer() {
        return container;
    }

    @Override
    public void setContainer(Container container) {
        this.container = container;
    }
}
