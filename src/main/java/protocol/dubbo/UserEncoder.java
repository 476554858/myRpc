package protocol.dubbo;

import framework.Invocation;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class UserEncoder extends MessageToByteEncoder<Invocation> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Invocation invocation, ByteBuf out) throws Exception {
        byte[] datas = ByteObjConverter.objectToByte(invocation);
        out.writeBytes(datas);
        ctx.flush();
    }
}