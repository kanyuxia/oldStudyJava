package note3.connector;

import note3.Connector;
import note3.Context;
import note3.Request;
import note3.Response;
import note3.Wrapper;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.Principal;
import java.util.*;

/**
 * Created by kanyuxia on 2017/4/26.
 * Http请求对象
 */
public class HttpRequest implements HttpServletRequest, Request {

    /**
     * The request attributes for this request.
     */
    private final HashMap<String, Object> attributes = new HashMap<>();

    /**
     * 存放Http request headers
     */
    private HashMap<String, ArrayList<String>> headers = new HashMap<>();

    /**
     * 存放Http request cookies
     */
    private ArrayList<Cookie> cookies = new ArrayList<>();

    /**
     * 存放Http请求参数：Query String or Form datas.
     * 只有当Servlet取参数时，才解析
     */
    private ParameterMap<String, String[]> parameterMap = null;

    /**
     * Have the parameters for this request been parsed yet?
     * 是否解析了Http Parameters
     */
    private boolean parameterPared = false;

    /**
     * The request URI associated with this request.
     * 请求URL地址
     */
    private String requestURI = null;

    /**
     * 请求协议
     */
    private String protocol = null;

    /**
     * The facade associated with this request.
     */
    private RequestFacade facade = new RequestFacade(this);

    /**
     * The input stream associated with this Request.
     */
    private InputStream input = null;

    /**
     * The socket associated with this Request.
     */
    private Socket socket = null;

    /**
     * The Response associated with this Request.
     */
    private Response response = null;

    /**
     * The Wrapper associated with this Request.
     */
    private Wrapper wrapper = null;

    /**
     * The Response associated with this Request.
     */
    private Context context = null;

    /**
     * The Connector through which this Request was received.
     */
    private Connector connector = null;


    // ---------------------------implements Request interface methods
    public void addHeader(String name, String value) {
        name = name.toLowerCase();
        ArrayList<String> values = headers.get(name);
        if (values != null) {
            values.add(value);
        }
        values = new ArrayList<>();
        headers.put(name, values);
    }

    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

    /**
     * 解析HTTP Parameters，如果已经解析则返回。
     */
    public void parseParameters() {
        if (parameterPared) {
            return;
        }
        parameterMap = new ParameterMap<>();
        parameterMap.setLocked(false);

        // 解析Query String or Form data

        parameterMap.setLocked(true);
    }

    public void addParameter(String name, String values[]) {
        parameterMap.put(name, values);
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    /**
     * Release all object references, and initialize instance variables, in
     * preparation for reuse of this object.
     * 清空所有数据
     */
    public void recycle() {
        attributes.clear();
        headers.clear();
        cookies.clear();
        if (parameterMap != null) {
            parameterMap.setLocked(false);
            parameterMap.clear();
        }
        parameterPared = false;
        requestURI = null;
        input = null;
        response = null;
        protocol = null;
        socket = null;
        wrapper = null;
        context = null;
    }

    @Override
    public Connector getConnector() {
        return connector;
    }

    @Override
    public void setConnector(Connector connector) {
        this.connector = connector;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public HttpServletRequest getRequest() {
        return facade;
    }

    @Override
    public Response getResponse() {
        return response;
    }

    @Override
    public void setResponse(Response response) {
        this.response = response;
    }

    @Override
    public Socket getSocket() {
        return socket;
    }

    @Override
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public InputStream getStream() {
        return input;
    }

    @Override
    public void setStream(InputStream stream) {
        this.input = stream;
    }

    @Override
    public Wrapper getWrapper() {
        return wrapper;
    }

    @Override
    public void setWrapper(Wrapper wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    // ----------------------------implements HttpServletRequest methods
    @Override
    public String getAuthType() {
        return null;
    }

    @Override
    public Cookie[] getCookies() {
        return new Cookie[0];
    }

    @Override
    public long getDateHeader(String name) {
        return 0;
    }

    @Override
    public String getHeader(String name) {
        return null;
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        return null;
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return null;
    }

    @Override
    public int getIntHeader(String name) {
        return 0;
    }

    @Override
    public String getMethod() {
        return null;
    }

    @Override
    public String getPathInfo() {
        return null;
    }

    @Override
    public String getPathTranslated() {
        return null;
    }

    @Override
    public String getContextPath() {
        return null;
    }

    @Override
    public String getQueryString() {
        return null;
    }

    @Override
    public String getRemoteUser() {
        return null;
    }

    @Override
    public boolean isUserInRole(String role) {
        return false;
    }

    @Override
    public Principal getUserPrincipal() {
        return null;
    }

    @Override
    public String getRequestedSessionId() {
        return null;
    }

    @Override
    public String getRequestURI() {
        return requestURI;
    }

    @Override
    public StringBuffer getRequestURL() {
        return null;
    }

    @Override
    public String getServletPath() {
        return null;
    }

    @Override
    public HttpSession getSession(boolean create) {
        return null;
    }

    @Override
    public HttpSession getSession() {
        return null;
    }

    @Override
    public String changeSessionId() {
        return null;
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromUrl() {
        return false;
    }

    @Override
    public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
        return false;
    }

    @Override
    public void login(String username, String password) throws ServletException {

    }

    @Override
    public void logout() throws ServletException {

    }

    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        return null;
    }

    @Override
    public Part getPart(String name) throws IOException, ServletException {
        return null;
    }

    @Override
    public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) throws IOException, ServletException {
        return null;
    }

    @Override
    public Object getAttribute(String name) {
        return null;
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public void setCharacterEncoding(String env) throws UnsupportedEncodingException {

    }

    @Override
    public int getContentLength() {
        return 0;
    }

    @Override
    public long getContentLengthLong() {
        return 0;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public String getParameter(String name) {
        parseParameters();
        return parameterMap.get(name)[0];
    }

    @Override
    public Enumeration<String> getParameterNames() {
        parseParameters();
        return new Enumerator<>(parameterMap.keySet());
    }

    @Override
    public String[] getParameterValues(String name) {
        parseParameters();
        return parameterMap.get(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        parseParameters();
        return parameterMap;
    }

    @Override
    public String getProtocol() {
        return protocol;
    }

    @Override
    public String getScheme() {
        return null;
    }

    @Override
    public String getServerName() {
        return null;
    }

    @Override
    public int getServerPort() {
        return 0;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return null;
    }

    @Override
    public String getRemoteAddr() {
        return null;
    }

    @Override
    public String getRemoteHost() {
        return null;
    }

    @Override
    public void setAttribute(String name, Object o) {

    }

    @Override
    public void removeAttribute(String name) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return null;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        return null;
    }

    @Override
    public String getRealPath(String path) {
        return null;
    }

    @Override
    public int getRemotePort() {
        return 0;
    }

    @Override
    public String getLocalName() {
        return null;
    }

    @Override
    public String getLocalAddr() {
        return null;
    }

    @Override
    public int getLocalPort() {
        return 0;
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        return null;
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
        return null;
    }

    @Override
    public boolean isAsyncStarted() {
        return false;
    }

    @Override
    public boolean isAsyncSupported() {
        return false;
    }

    @Override
    public AsyncContext getAsyncContext() {
        return null;
    }

    @Override
    public DispatcherType getDispatcherType() {
        return null;
    }


    // ---------------------implements Request interfaces methods

}
