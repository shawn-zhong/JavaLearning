package com.tseong.learning.advance._06_proxy;

interface Movie {
    void play();
}

class RealMovie implements Movie {

    @Override
    public void play() {
        System.out.println("您正在观看电影<<肖申克的救赎>>");
    }
}

class Cinema implements Movie {

    RealMovie movie;

    public Cinema(RealMovie movie) {
        super();
        this.movie = movie;
    }

    @Override
    public void play() {
        advertisement(true);
        movie.play();
        advertisement(false);
    }

    public void advertisement(boolean isStart) {
        if (isStart) {
            System.out.println("电影马上开始了，爆米花、可乐、口香糖9.8折，快来买啊！");
        } else {
            System.out.println("电影马上结束了，爆米花、可乐、口香糖9.8折，买回家吃吧！");
        }
    }
}


public class ProxyDemo1 {

    public static void main(String ... args) {
        RealMovie realMovie = new RealMovie();
        Movie movie = new Cinema(realMovie);
        movie.play();
    }

}


/*

静态代理总结:
1.可以做到在不修改目标对象的功能前提下,对目标功能扩展.
2.缺点:

因为代理对象需要与目标对象实现一样的接口,所以会有很多代理类,类太多.同时,一旦接口增加方法,目标对象与代理对象都要维护.
如何解决静态代理中的缺点呢?答案是可以使用动态代理方式

 */