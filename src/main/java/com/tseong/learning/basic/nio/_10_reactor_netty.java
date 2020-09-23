package com.tseong.learning.basic.nio;

public class _10_reactor_netty {

    /**
     *
     * https://www.jianshu.com/p/1ccbc6a348db
     *  Netty 那些事儿 ——— Reactor模式详解
     */
}



/*
*
* 故事：老王烧开水。

出场人物：老张，水壶两把（普通水壶，简称水壶；会响的水壶，简称响水壶）。

老王想了想，有好几种等待方式

1.老王用水壶煮水，并且站在那里，不管水开没开，每隔一定时间看看水开了没。－同步阻塞

老王想了想，这种方法不够聪明。

2.老王还是用水壶煮水，不再傻傻的站在那里看水开，跑去寝室上网，但是还是会每隔一段时间过来看看水开了没有，水没有开就走人。－同步非阻塞

老王想了想，现在的方法聪明了些，但是还是不够好。

3.老王这次使用高大上的响水壶来煮水，站在那里，但是不会再每隔一段时间去看水开，而是等水开了，水壶会自动的通知他。－异步阻塞

老王想了想，不会呀，既然水壶可以通知我，那我为什么还要傻傻的站在那里等呢，嗯，得换个方法。

4.老王还是使用响水壶煮水，跑到客厅上网去，等着响水壶自己把水煮熟了以后通知他。－异步非阻塞

老王豁然，这下感觉轻松了很多。


同步和异步

同步就是烧开水，需要自己去轮询（每隔一段时间去看看水开了没），异步就是水开了，然后水壶会通知你水已经开了，你可以回来处理这些开水了。
同步和异步是相对于操作结果来说，会不会等待结果返回。

阻塞和非阻塞

阻塞就是说在煮水的过程中，你不可以去干其他的事情，非阻塞就是在同样的情况下，可以同时去干其他的事情。阻塞和非阻塞是相对于线程是否被阻塞。

https://blog.csdn.net/qq_33314107/article/details/80766381


*
*
* */


// https://zhuanlan.zhihu.com/p/22834126