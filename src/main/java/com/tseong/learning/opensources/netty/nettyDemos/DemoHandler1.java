package com.tseong.learning.opensources.netty.nettyDemos;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class DemoHandler1 extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        while (byteBuf.isReadable()) {
            byte temp = byteBuf.readByte();
            String str = String.format("%c", temp);
            System.out.println(str);
            list.add(str);
        }
    }
}
