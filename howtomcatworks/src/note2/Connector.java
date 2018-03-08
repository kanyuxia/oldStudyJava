package note2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by kanyuxia on 2017/4/27.
 * 模拟org.apache.catalina.Connector接口
 */
public interface Connector {
    /**
     * Return the Container used for processing requests received by this
     * Connector.
     */
    Container getContainer();

    /**
     * Set the Container used for processing requests received by this
     * Connector.
     *
     * @param container The new Container to use
     */
    void setContainer(Container container);

    /**
     * Return the scheme that will be assigned to requests received
     * through this connector.  Default value is "http".
     */
    String getScheme();

    /**
     * Set the scheme that will be assigned to requests received through
     * this connector.
     *
     * @param scheme The new scheme
     */
    void setScheme(String scheme);

    /**
     * Create (or allocate) and return a Request object suitable for
     * specifying the contents of a Request to the responsible Container.
     */
    HttpServletRequest createRequest();

    /**
     * Create (or allocate) and return a Response object suitable for
     * receiving the contents of a Response from the responsible Container.
     */
    HttpServletResponse createResponse();

    /**
     * Invoke a pre-startup initialization. This is used to allow connectors
     * to bind to restricted ports under Unix operating environments.
     */
    void initialize();

}
