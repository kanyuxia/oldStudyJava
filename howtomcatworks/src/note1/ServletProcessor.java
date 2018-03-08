package note1;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by kanyuxia on 2017/4/24.
 * HttpServer Servelt请求处理类
 */
public class ServletProcessor {
    /**
     * 处理Servlet请求方法
     * @param request 请求对象
     * @param response 响应对象
     */
    public void process(Request request, Response response) {
        String url = request.getUrl();
        String servletName = url.substring(url.lastIndexOf("/") + 1);
        // 从容器中拿到该Servlet
        Servlet servlet = ServletContainer.container.get(servletName);
        if (servlet != null) {
            // 使用门面模式
            RequestFacade requestFacade = new RequestFacade(request);
            ResponseFacade responseFacade = new ResponseFacade(response);
            try {
                servlet.service(requestFacade, responseFacade);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        // Servlet不存在
        response.sendNotFound();
    }
}
