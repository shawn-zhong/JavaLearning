package com.tseong.learning.opensources.nettyDiscard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/*
 服务器端处理通道，这里知识打印以下请求的内容，并不对请求进行任何的响应。
 DiscardServerHandler继承自ChannelHandlerAdapter， 这个类实现了ChannelHandler接口，
 ChannelHandler提供了许多事件处理的接口方法，然后你可以覆盖这些方法。现在仅仅需要继承ChannelHandlerAdapter
 类而不是你自己去实现接口方法
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    /*
    每当从客户端收到新的数据时，这个方法会在收到消息时被调用。这个例子中，收到的消息的类型是ByteBuf

    @param ctx ： 通道处理的上下文信息

    @param msg ： 接受的消息

     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        /*
        try {
            ByteBuf in = (ByteBuf) msg;
            System.out.println(in.toString(CharsetUtil.UTF_8));
        } finally {
            // ByteBuf 是一个引用计数对象，这个对象必须显示的调用release（）方法来释放，请记住处理器的职责是释放所有传递到处理器的引用计数对象
            // 抛弃收到的数据
            ReferenceCountUtil.release(msg);
        }*/

        ctx.write(msg);
        ctx.flush();


    }

    /*
    这个方法会在发生异常的时候触发
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        /*
        exceptionCaught（）事件处理方法是当出现Throwable对象才会被调用。即当netty由于IO错误或者处理器在
        处理事件时抛出的异常时。在大部分情况下，捕获的异常应该被记录下来并且把关联的channel给关闭掉。然而这个方法的处理方式
        会在遇到不同异常的情况下有不同的实现，比如你可能想在关闭连接之前发送一个错误码的响应消息
         */

        // 出现异常就关闭
        cause.printStackTrace();
        ctx.close();
    }
}
