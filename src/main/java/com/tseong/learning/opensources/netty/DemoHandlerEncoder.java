package com.tseong.learning.opensources.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class DemoHandlerEncoder extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {

        System.out.println("This is encoder");

        String str = String.format("Encoder : %s", (String)msg);
        byte[] buf = ((String) str).getBytes();

        ByteBuf encoded = ctx.alloc().buffer(buf.length);
        encoded.writeBytes(buf);
        ctx.write(encoded, promise);
    }
}
