package framework;

import protocol.http.HttpClient;
import provider.api.HelloService;
import register.Register;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory<T> {

    public static <T> T getProxy(final Class interfaceClass){
       return (T)Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Protocol protocol = ProtocolFactory.getProtocol();
                Invocation invocation = new Invocation(HelloService.class.getName(),"sayHello",new Object[]{"bug zhang"},new Class[]{String.class});
                URL url = Register.random(interfaceClass.getName());
                return protocol.send(url,invocation);
            }
        });
    }
}
