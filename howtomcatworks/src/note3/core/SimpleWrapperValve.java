package note3.core;

import note3.*;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by kanyuxia on 2017/5/5.
 */
public class SimpleWrapperValve implements Valve, Contained {

    private Container container;

    SimpleWrapperValve(Container container) {
        this.container = container;
    }

    @Override
    public void invoke(Request request, Response response, ValveContext valveContext) throws IOException, ServletException {
        SimpleWrapper wrapper = (SimpleWrapper) container;
        HttpServletRequest httpServletRequest = request.getRequest();
        HttpServletResponse httpServletResponse = response.getResponse();
        Servlet servlet = wrapper.allocate();
        servlet.service(httpServletRequest, httpServletResponse);
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
