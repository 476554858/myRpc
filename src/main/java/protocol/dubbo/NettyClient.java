package protocol.dubbo;

import framework.Invocation;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;

import java.util.concurrent.TimeUnit;

public class NettyClient {

    public String send(String hostName, Integer port, Invocation invocation) {
        Channel channel = null;
        try {
            final NettyClientHandler nettyClientHandler = new NettyClientHandler();
            //1.创建一个线程组
            EventLoopGroup group=new NioEventLoopGroup();
            //2.创建客户端的启动助手，完成相关配置
            Bootstrap b=new Bootstrap();
            b.group(group) //3.设置线程组
                    .channel(NioSocketChannel.class)  //4.设置客户端通道的实现类
                    .handler(new ChannelInitializer<SocketChannel>() { //5.创建一个初始胡对象
                        @Override//6.往pipeline通道中添加自定义的handler
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                            pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new UserEncoder());
                            pipeline.addLast(nettyClientHandler);
                        }
                    });

            System.out.println(".......Client is ready");

            //7.启动客户端去连接服务器端 connect方法是异步的 sync方法是同步非阻塞的
            ChannelFuture cf = b.connect(hostName,port).sync();

            ChannelPromise promise = nettyClientHandler.sendMessage(invocation);
            promise.await(3, TimeUnit.SECONDS);
            String result = (String)nettyClientHandler.getResponse();
            System.out.println("reuslt:"+result);
            channel = cf.channel();
            //8.关闭连接（异步非阻塞）
            cf.channel().closeFuture().sync();
            return result;


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            channel.close();
        }
        return null;
    }

}
