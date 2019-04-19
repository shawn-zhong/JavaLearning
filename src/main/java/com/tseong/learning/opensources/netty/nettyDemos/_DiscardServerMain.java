package com.tseong.learning.opensources.netty.nettyDemos;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class _DiscardServerMain {

    public static void main(String[] args) {

        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                            nioSocketChannel.pipeline()
                                    .addLast("encoder", new DemoHandlerEncoder())
                                    //.addLast("decoder", new DemoHandler1())
                                    //.addLast("readTimeoutHandler", new ReadTimeoutHandler(30))
                                    //.addLast(new DemoHandler2())
                                    .addLast(new DiscardHandler());
                        }
                    })
                    .option(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = serverBootstrap.bind(8181).sync();
            f.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            childGroup.shutdownGracefully();
            parentGroup.shutdownGracefully();
            System.out.println("exit...");
        }
    }
}


/*
http://blog.csdn.net/excellentyuxiao/article/details/53390408
http://www.importnew.com/15656.html

说完Reacotr模型的三种形式，那么Netty是哪种呢？其实，我还有一种Reactor模型的变种没说，那就是去掉线程池的第三种形式的变种，这也 是Netty NIO的默认模式。
在实现上，Netty中的Boss类充当mainReactor，NioWorker类充当subReactor（默认 NioWorker的个数是Runtime.getRuntime().availableProcessors()）。
在处理新来的请求 时，NioWorker读完已收到的数据到ChannelBuffer中，之后触发ChannelPipeline中的ChannelHandler流。

Netty是事件驱动的，可以通过ChannelHandler链来控制执行流向。因为ChannelHandler链的执行过程是在 subReactor中同步的，所以如果业务处理handler耗时长，将严重影响可支持的并发数。
这种模型适合于像Memcache这样的应用场景，但 对需要操作数据库或者和其他模块阻塞交互的系统就不是很合适。Netty的可扩展性非常好，而像ChannelHandler线程池化的需要，
可以通过在 ChannelPipeline中添加Netty内置的ChannelHandler实现类–ExecutionHandler实现，对使用者来说只是 添加一行代码而已。对于ExecutionHandler需要的线程池模型，
Netty提供了两种可 选：
1） MemoryAwareThreadPoolExecutor 可控制Executor中待处理任务的上限（超过上限时，后续进来的任务将被阻 塞），并可控制单个Channel待处理任务的上限；
2） OrderedMemoryAwareThreadPoolExecutor 是  MemoryAwareThreadPoolExecutor 的子类，它还可以保证同一Channel中处理的事件流的顺序性，这主要是控制事件在异步处
    理模式下可能出现的错误的事件顺序，但它并不保证同一Channel中的事件都在一个线程中执行（通常也没必要）。一般来 说，OrderedMemoryAwareThreadPoolExecutor 是个很不错的选择，
    当然，如果有需要，也可以DIY一个

 */