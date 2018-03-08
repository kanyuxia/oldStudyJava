package note3.startup;

import note3.Context;
import note3.Lifecycle;
import note3.Mapper;
import note3.Wrapper;
import note3.connector.HttpConnector;
import note3.core.SimpleContext;
import note3.core.SimpleContextMapper;
import note3.core.SimpleWrapper;

import java.io.IOException;

/**
 * Created by kanyuxia on 2017/5/6.
 */
public class BootStrap {
    public static void main(String[] args) {

        // Create Wrapper
        Wrapper wrapper = new SimpleWrapper();
        wrapper.setName("Primitive");
        wrapper.setServletClass("PrimitiveServlet");
        Wrapper wrapper1 = new SimpleWrapper();
        wrapper1.setName("Modern");
        wrapper1.setServletClass("Modern");

        // Create Context
        Context context = new SimpleContext();
        context.setName("simpleContext");

        // Context add Wrapper
        context.addChild(wrapper);
        context.addChild(wrapper1);

        // Create Mapper
        Mapper mapper = new SimpleContextMapper();
        mapper.setProtocol("http/1.1");

        // Context add Mapper
        context.addMapper(mapper);

        // context.addServletMapping(pattern, name);
        context.addServletMapping("/servlet/primitive", "Primitive");
        context.addServletMapping("/servlet/modern", "Modern");

        // create、initialize and start connector
        HttpConnector connector = new HttpConnector();
        connector.initialize();
        connector.start();
        connector.setContainer(context);

        try {
            int b = System.in.read();
            if (context instanceof Lifecycle) {
                ((Lifecycle) context).stop();
            }
            if (connector instanceof Lifecycle) {
                ((Lifecycle) connector).stop();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
