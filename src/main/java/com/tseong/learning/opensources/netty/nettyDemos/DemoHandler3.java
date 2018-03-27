package com.tseong.learning.opensources.netty.nettyDemos;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.ReadTimeoutException;

public class DemoHandler3 extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String str = String.format("Handler3:%s", (String)msg);
        System.out.println(str);
        //ctx.writeAndFlush((String)msg);
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
