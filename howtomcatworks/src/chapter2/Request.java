package chapter2;

import javax.servlet.*;
import java.io.*;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * Created by kanyuxia on 2017/4/19.
 * Request类：实现了ServletRequest接口，如果HTTP请求是Servlet，则服务器创建ServletRequest和ServletResponse对象，
 * 并传入Servlet的service()方法。
 */
public class Request implements ServletRequest {
    private final InputStream inputStream;
    private String url;

    public Request(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * 解析请求方法
     */
    public void parse() {
        BufferedInputStream in = new BufferedInputStream(inputStream);
        // 读取HTTP请求一些字节
        StringBuilder firstLine = new StringBuilder(1024);
        byte[] buffer = new byte[1024];
        int byteNum = 0;
        try {
            byteNum = in.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < byteNum; i++) {
            firstLine.append((char) buffer[i]);
        }
        // 解析URL
        int index = firstLine.toString().indexOf(" ");
        int index1 = firstLine.toString().indexOf(" ", index + 1);
        url = firstLine.toString().substring(index + 1, index1);
    }

    public String getUrl() {
        return url;
    }

    // 实现ServletRequest接口的方法
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
        return null;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return null;
    }

    @Override
    public String[] getParameterValues(String name) {
        return new String[0];
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return null;
    }

    @Override
    public String getProtocol() {
        return null;
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
}
