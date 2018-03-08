package note3;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * <p>A <b>ValveContext</b> is the mechanism by which a Valve can trigger the
 * execution of the next Valve in a Pipeline, without having to know anything
 * about the internal implementation mechanisms.  An instance of a class
 * implementing this interface is passed as a parameter to the
 * <code>Valve.invoke()</code> method of each executed Valve.</p>
 *
 * <p><strong>IMPLEMENTATION NOTE</strong>: It is up to the implementation of
 * ValveContext to ensure that simultaneous requests being processed (by
 * separate threads) through the same Pipeline do not interfere with each
 * other's flow of control.</p>
 *
 * Created by kanyuxia on 2017/5/4.
 * 模拟org.apache.catalina.ValveContext接口：简化了许多的方法，方法的说明来自源码.
 */
public interface ValveContext {
    //---------------------------------------------------------- Public Methods


    /**
     * Cause the <code>invoke()</code> method of the next Valve that is part of
     * the Pipeline currently being processed (if any) to be executed, passing
     * on the specified request and response objects plus this
     * <code>ValveContext</code> instance.  Exceptions thrown by a subsequently
     * executed Valve (or a Filter or Servlet at the application level) will be
     * passed on to our caller.
     *
     * If there are no more Valves to be executed, an appropriate
     * ServletException will be thrown by this ValveContext.
     *
     * @param request The request currently being processed
     * @param response The response currently being created
     *
     * @exception IOException if thrown by a subsequent Valve, Filter, or
     *  Servlet
     * @exception ServletException if thrown by a subsequent Valve, Filter,
     *  or Servlet
     * @exception ServletException if there are no further Valves configured
     *  in the Pipeline currently being processed
     */
    void invokeNext(Request request, Response response)
            throws IOException, ServletException;
}
