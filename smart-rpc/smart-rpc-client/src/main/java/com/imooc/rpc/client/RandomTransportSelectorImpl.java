package com.imooc.rpc.client;

import com.imooc.rpc.common.util.ReflectionUtil;
import com.imooc.rpc.protocol.Peer;
import com.imooc.rpc.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@Slf4j
public class RandomTransportSelectorImpl implements TransportSelector {

    private List<TransportClient> availableClients;

    public RandomTransportSelectorImpl() {
        this.availableClients = new ArrayList<>();
    }

    @Override
    public synchronized void init(List<Peer> peers, Integer count, Class<? extends TransportClient> clazz) {
        int size = Math.max(count, 1);
        peers.stream().forEach(peer -> IntStream.range(0, size)
                .forEach(i -> {
                    this.availableClients.add(ReflectionUtil.newInstance(clazz).connect(peer));
                    log.info("Connect server: {}", peer);
                }));
    }

    @Override
    public synchronized TransportClient select() {
        int i = ThreadLocalRandom.current().nextInt(this.availableClients.size());
        return this.availableClients.remove(i);
    }

    @Override
    public synchronized void release(TransportClient client) {
        this.availableClients.add(client);
    }

    @Override
    public synchronized void close() {
        this.availableClients.stream().forEach(TransportClient::close);
        this.availableClients.clear();
    }

}
