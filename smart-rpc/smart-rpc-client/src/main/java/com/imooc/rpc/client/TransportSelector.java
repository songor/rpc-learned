package com.imooc.rpc.client;

import com.imooc.rpc.protocol.Peer;
import com.imooc.rpc.transport.TransportClient;

import java.util.List;

public interface TransportSelector {

    void init(List<Peer> peers, Integer count, Class<? extends TransportClient> clazz);

    TransportClient select();

    void release(TransportClient client);

    void close();

}
