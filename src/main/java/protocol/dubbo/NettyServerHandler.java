package protocol.dubbo;

import framework.Invocation;
import framework.URL;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import register.Register;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;

public class NettyServerHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("666");
        Invocation invocation = (Invocation)msg;

        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().localAddress();
        Class serviceImpl = Register.get(new URL("localhost",insocket.getPort()),invocation.getInterfaceName());

        Method method = serviceImpl.getMethod(invocation.getMethodName(),invocation.getParamTypes());
        Object result = method.invoke(serviceImpl.newInstance(),invocation.getParams());

        System.out.println("netty================"+result);
        ctx.writeAndFlush(result);
    }

}
