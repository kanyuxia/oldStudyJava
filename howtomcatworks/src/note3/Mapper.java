package note3;

/**
 * Interface defining methods that a parent Container may implement to select
 * a subordinate Container to process a particular Request, optionally
 * modifying the properties of the Request to reflect the selections made.
 * <p>
 * A typical Container may be associated with a single Mapper that processes
 * all requests to that Container, or a Mapper per request protocol that allows
 * the same Container to support multiple protocols at once.
 *
 * Created by kanyuxia on 2017/5/4.
 * 模拟org.apache.catalina.Mapper接口：简化了许多的方法，方法的说明来自源码.
 */
public interface Mapper {
    /**
     * Return the Container with which this Mapper is associated.
     */
    Container getContainer();

    /**
     * Set the Container with which this Mapper is associated.
     *
     * @param container The newly associated Container
     *
     * @exception IllegalArgumentException if this Container is not
     *  acceptable to this Mapper
     */
    void setContainer(Container container);

    /**
     * Return the protocol for which this Mapper is responsible.
     */
    String getProtocol();

    /**
     * Set the protocol for which this Mapper is responsible.
     *
     * @param protocol The newly associated protocol
     */
    void setProtocol(String protocol);

    /**
     * Return the child Container that should be used to process this Request,
     * based upon its characteristics.  If no such child Container can be
     * identified, return <code>null</code> instead.
     *
     * @param request Request being processed
     * @param update Update the Request to reflect the mapping selection?
     */
    Container map(Request request, boolean update);
}
