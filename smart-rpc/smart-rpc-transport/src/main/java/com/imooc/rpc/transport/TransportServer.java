package com.imooc.rpc.transport;

/**
 * 1、启动并监听端口
 * 2、接受请求
 * 3、关闭监听端口
 */
public interface TransportServer {

    void init(Integer port, RequestHandler handler);

    void start();

    void stop();

}
