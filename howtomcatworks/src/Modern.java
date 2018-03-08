import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by kanyuxia on 2017/5/6.
 */
public class Modern implements Servlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("Primitive.init()");
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        String header = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html;charset=UTF-8\r\n"
                + "\r\n";
        String content = "<html>\r\n" +
                "<head>" +
                "</head>" +
                "<body>" +
                "Modern Servlet" +
                "</body>" +
                "</html>";
        PrintWriter writer = res.getWriter();
        writer.write(header);
        writer.write(content);
        writer.flush();
        writer.close();
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {
        System.out.println("Primitive.destory()");
    }
}
