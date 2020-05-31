package consumer;

import framework.ProxyFactory;
import provider.api.HelloService;

public class Consumer {
    public static void main(String[] args) throws Exception{
        HelloService helloService = ProxyFactory.getProxy(HelloService.class);
        String result = helloService.sayHello("bug zhang");
        System.out.println(result);
    }
}
