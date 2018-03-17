package com.tseong.learning.opensources.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.ReadTimeoutException;

public class DemoHandler2 extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String str = String.format("Handler2:%s", (String) msg);
        System.out.println(str);
        ctx.fireChannelRead(str);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        if (cause instanceof ReadTimeoutException) {
            String notify = "timeout!";
            ctx.writeAndFlush(notify);
            return;
        }

        cause.printStackTrace();
    }
}
