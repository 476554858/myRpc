package protocol.http;

import framework.Invocation;
import framework.URL;
import org.apache.commons.io.IOUtils;
import register.Register;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

public class HttpServerHandler {
    public void handler(HttpServletRequest request, HttpServletResponse response) throws Exception, ClassNotFoundException {
        InputStream inputStream = request.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(inputStream);
        Invocation invocation = (Invocation) ois.readObject();

        //找到实现类
        String interfaceName = invocation.getInterfaceName();
        URL url = new URL("localhost",8080);
        Class implClass = Register.get(url, interfaceName);

        Method method = implClass.getMethod(invocation.getMethodName(),invocation.getParamTypes());
        String result = (String) method.invoke(implClass.newInstance(),invocation.getParams());

        OutputStream outputStream = response.getOutputStream();
        IOUtils.write(result,outputStream);
    }
}
