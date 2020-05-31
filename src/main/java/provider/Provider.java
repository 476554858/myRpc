package provider;

import framework.URL;
import protocol.http.HttpServer;
import provider.api.HelloService;
import provider.impl.HelloServiceImpl;
import register.Register;

public class Provider {
    public static void main(String[] args) throws Exception{
        //注册服务
        URL url = new URL("localhost",8080);
        Register.regist(url, HelloService.class.getName(), HelloServiceImpl.class);

        //启动服务
        HttpServer httpServer = new HttpServer();
        httpServer.start(url.getHostName(),url.getPort());
    }
}
