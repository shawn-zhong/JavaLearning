package com.tseong.learning.opensources.netty._02_TimeServerClient.demo;

import com.tseong.learning.opensources.netty._02_TimeServerClient.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * @author: Shawn Zhong @date: 3/1/19 11:55 AM
 */
public class TelnetDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //if (in.readableBytes() < 6) {
        //    return; // 3
        //}

        System.out.println("len:" + in.readableBytes());
        String inStr = in.toString(CharsetUtil.UTF_8);
        System.out.println("str:" + inStr);
        if (inStr.startsWith("now")) {

        /*
        如果在 decode() 方法里增加了一个对象到 out 对象里，这意味着解码器解码消息成功。ByteToMessageDecoder 将会丢弃在累积缓冲里已经被读过的数据。
        请记得你不需要对多条消息调用 decode()，ByteToMessageDecoder 会持续调用 decode() 直到不放任何数据到 out 里
         */
            //list.add(byteBuf.readBytes(4));
            // 改用结构类
            System.out.println("str:" + inStr);

            out.add(true);
        }
    }
}
