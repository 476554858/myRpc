package protocol.http;

import framework.Invocation;
import framework.Protocol;
import framework.URL;

public class HttpPortocol implements Protocol{
    @Override
    public void start(URL url) throws Exception {
        HttpServer httpServer = new HttpServer();
        httpServer.start(url.getHostName(),url.getPort());
    }

    @Override
    public String send(URL url, Invocation invocation) throws Exception {
        HttpClient httpClient = new HttpClient();
        return httpClient.post(url.getHostName(),url.getPort(),invocation);
    }
}
