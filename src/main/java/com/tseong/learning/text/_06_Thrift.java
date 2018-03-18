package com.tseong.learning.text;

public class _06_Thrift {

    /*

    thrift包含三个主要组件:

    - Protocal : TBinaryProtocal, TCompactProtocal, TJSONProtocal, TDebugProtocal
    - transport
    - Server :
        1) TSimpleServer（测试用） : 吱哟一个线程阻塞式接受和处理请求，所以只能服务于一个客户端，其它客户端在被连接之前只能等待
        2) TNonblokingServer : 使用了NIO Selector技术，但只有一个线程接受请求和处理读写，其它连接还是要等待
        3) THSHaServer : 类似上面但使用一个独立的worker线程池来处理消息，使用一个线程来处理网络I/O和数据读取
        4）TThreadPoolServer : 数据读取和业务处理都会交给线程池处理；主线程or单线程只负责监听新连接
        5) TThreadedSelectorServer : 使用了多线程来处理I/O. 维护了两个线程池 (优势在于网络I/O是瓶颈时)


     */


}
