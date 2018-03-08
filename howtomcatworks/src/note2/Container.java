package note2;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by kanyuxia on 2017/4/27.
 * 模拟org.apache.catalina.Container接口
 */
public interface Container {
    /**
     * Process the specified Request, and generate the corresponding Response,
     * according to the design of this particular Container.
     *
     * @param request Request to be processed
     * @param response Response to be produced
     *
     * @exception IOException if an input/output error occurred while
     *  processing
     * @exception ServletException if a ServletException was thrown
     *  while processing this request
     */
    void invoke(HttpRequest request, HttpResponse response) throws IOException, ServletException;
}
