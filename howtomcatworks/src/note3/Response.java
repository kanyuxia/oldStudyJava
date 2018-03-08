package note3;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * Created by kanyuxia on 2017/5/6.
 */
public interface Response {
    /**
     * Return the Connector through which this Response is returned.
     */
    public Connector getConnector();


    /**
     * Set the Connector through which this Response is returned.
     *
     * @param connector The new connector
     */
    public void setConnector(Connector connector);

    /**
     * Return the Context with which this Response is associated.
     */
    public Context getContext();

    /**
     * Set the Context with which this Response is associated.  This should
     * be called as soon as the appropriate Context is identified.
     *
     * @param context The associated Context
     */
    public void setContext(Context context);

    /**
     * Return the Request with which this Response is associated.
     */
    public Request getRequest();


    /**
     * Set the Request with which this Response is associated.
     *
     * @param request The new associated request
     */
    public void setRequest(Request request);


    /**
     * Return the <code>HttpServletResponse</code> for which this object
     * is the facade.
     */
    public HttpServletResponse getResponse();


    /**
     * Return the output stream associated with this Response.
     */
    public OutputStream getStream();


    /**
     * Set the output stream associated with this Response.
     *
     * @param stream The new output stream
     */
    public void setStream(OutputStream stream);


    /**
     * Release all object references, and initialize instance variables, in
     * preparation for reuse of this object.
     */
    public void recycle();
}
