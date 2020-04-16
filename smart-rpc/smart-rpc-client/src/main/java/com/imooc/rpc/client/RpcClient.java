package com.imooc.rpc.client;

import com.imooc.rpc.codec.Decoder;
import com.imooc.rpc.codec.Encoder;
import com.imooc.rpc.common.util.ReflectionUtil;

import java.lang.reflect.Proxy;

public class RpcClient {

    private RpcClientConfig config;

    private Encoder encoder;

    private Decoder decoder;

    private TransportSelector selector;

    public RpcClient() {
        this(new RpcClientConfig());
    }

    public RpcClient(RpcClientConfig config) {
        this.config = config;
        this.encoder = ReflectionUtil.newInstance(this.config.getEncoderClass());
        this.decoder = ReflectionUtil.newInstance(this.config.getDecoderClass());
        this.selector = ReflectionUtil.newInstance(this.config.getSelectorClass());
        this.selector.init(this.config.getPeers(), this.config.getCount(), this.config.getTransportClass());
    }

    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{clazz},
                new RemoteInvocationHandler(clazz, encoder, decoder, selector));
    }

}
