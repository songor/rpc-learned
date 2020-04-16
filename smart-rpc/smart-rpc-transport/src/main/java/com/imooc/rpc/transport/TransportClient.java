package com.imooc.rpc.transport;

import com.imooc.rpc.protocol.Peer;

import java.io.InputStream;

/**
 * 1、创建连接
 * 2、发送数据，并且等待响应
 * 3、关闭连接
 */
public interface TransportClient {

    TransportClient connect(Peer peer);

    InputStream write(InputStream data);

    void close();

}
