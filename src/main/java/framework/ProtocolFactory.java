package framework;

import protocol.dubbo.DubboProtocol;
import protocol.http.HttpPortocol;

public class ProtocolFactory {

    public static Protocol getProtocol(){
        String name = System.getProperty("proto");
        if(name == null || name.equals("")) name = "http";
        if ("http".equals(name)) {
            return new HttpPortocol();
        } else if ("dubbo".equals(name)) {
            return new DubboProtocol();
        }
        return new HttpPortocol();
    }
}
