package note3;

/**
 * A <b>Context</b> is a Container that represents a servlet context, and
 * therefore an individual web applicaiton, in the Catalina servlet engine.
 * It is therefore useful in almost every deploymentof Catalina (even if a
 * Connector attached to a web server (such as Apache) uses the web server's
 * facilities to identify the appropriate Wrapper to handle this request.
 * It also provides a convenient mechanism to use Interceptors that see
 * every request processed by this particular web application.
 * <p>
 * The parent Container attached to a Context is generally a Host, but may
 * be some other implementation, or may be omitted if it is not necessary.
 * <p>
 * The child containers attached to a Context are generally implementations
 * of Wrapper (representing individual servlet definitions).
 * <p>
 *
 * Created by kanyuxia on 2017/5/4.
 * 模拟org.apache.catalina.Context接口：简化了许多的方法，方法的说明来自源码.
 */
public interface Context extends Container {
    /**
     * Add a new servlet mapping, replacing any existing mapping for
     * the specified pattern.
     *
     * @param pattern URL pattern to be mapped
     * @param name Name of the corresponding servlet to execute
     */
    void addServletMapping(String pattern, String name);

    /**
     * Return the servlet name mapped by the specified pattern (if any);
     * otherwise return <code>null</code>.
     *
     * @param pattern Pattern for which a mapping is requested
     */
    String findServletMapping(String pattern);

}
