package com.tseong.learning.opensources.netty._02_TimeServerClient;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class TimeEncoder extends ChannelOutboundHandlerAdapter{

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        UnixTime m = (UnixTime) msg;
        ByteBuf encoded = ctx.alloc().buffer(4);
        encoded.writeInt((int)m.value());
        ctx.write(encoded, promise);

       /*
        byte[] sample = "helloJoy\n".getBytes();
        ByteBuf encoded = ctx.alloc().buffer(sample.length);
        encoded.writeBytes(sample);
        ctx.write(encoded, promise);
       */
    }
}
