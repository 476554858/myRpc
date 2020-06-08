package framework;

public interface Protocol {
    void start(URL url) throws Exception;
    String send(URL url,Invocation invocation) throws Exception;
}
