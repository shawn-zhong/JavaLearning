package com.tseong.learning.opensources.netty._02_TimeServerClient;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/*
一个32位整型是非常小的数据，他并不见得会被经常拆分到到不同的数据段内。然而，问题是他确实可能会被拆分到不同的数据段内，并且拆分的可能性会随着通信量的增加而增加。
最简单的方案是构造一个内部的可积累的缓冲，直到4个字节全部接收到了内部缓冲。下面的代码修改了 TimeClientHandler 的实现类修复了这个问题
 */
public class TimeClientHandler2 extends ChannelInboundHandlerAdapter {

    private ByteBuf buf;

    // ChannelHandler 有2个生命周期的监听方法：handlerAdded()和 handlerRemoved()。你可以完成任意初始化任务只要他不会被阻塞很长的时间。
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        buf = ctx.alloc().buffer(4);    // 1
    }

    @Override
    // 首先，所有接收的数据都应该被累积在 buf 变量里
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        buf.release();  // 1
        buf = null;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf m = (ByteBuf) msg;
        buf.writeBytes(m); // 2
        m.release();

        // 然后，处理器必须检查 buf 变量是否有足够的数据，在这个例子中是4个字节，然后处理实际的业务逻辑。否则，
        // Netty 会重复调用channelRead() 当有更多数据到达直到4个字节的数据被积累。
        if (buf.readableBytes() >= 4) { // 3
            long currentTimeMills = (buf.readUnsignedInt() - 2208988800L) * 1000L;
            System.out.println(new Date(currentTimeMills));
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
