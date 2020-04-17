package com.imooc.rpc.server;

import com.imooc.rpc.codec.Decoder;
import com.imooc.rpc.codec.Encoder;
import com.imooc.rpc.common.util.ReflectionUtil;
import com.imooc.rpc.protocol.Request;
import com.imooc.rpc.protocol.Response;
import com.imooc.rpc.transport.RequestHandler;
import com.imooc.rpc.transport.TransportServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

@Slf4j
public class RpcServer {

    private RpcServerConfig config;

    private TransportServer transportServer;

    private Encoder encoder;

    private Decoder decoder;

    private ServiceManager manager;

    private ServiceInvoker invoker;

    public RpcServer() {
        this(new RpcServerConfig());
    }

    public RpcServer(RpcServerConfig config) {
        this.config = config;
        this.transportServer = ReflectionUtil.newInstance(config.getTransportClass());
        this.transportServer.init(config.getPort(), this.handler);
        this.encoder = ReflectionUtil.newInstance(config.getEncoderClass());
        this.decoder = ReflectionUtil.newInstance(config.getDecoderClass());
        this.manager = new ServiceManager();
        this.invoker = new ServiceInvoker();
    }

    private RequestHandler handler = (in, out) -> {
        Response response = new Response();
        try {
            byte[] bytes = IOUtils.readFully(in, in.available());
            Request request = this.decoder.decode(bytes, Request.class);
            log.info("Inbound: {}", request);
            ServiceInstance instance = this.manager.lookup(request);
            Object result = this.invoker.invoke(instance, request);
            response.setData(result);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response.setCode(1);
            response.setMessage("RPC invoke got error: " + e.getClass().getName() + " : " + e.getMessage());
        } finally {
            try {
                byte[] bytes = this.encoder.encode(response);
                out.write(bytes);
                log.info("Outbound: {}", response);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    };

    public <T> void register(Class<T> interfaceClass, T bean) {
        this.manager.register(interfaceClass, bean);
    }

    public void start() {
        this.transportServer.start();
    }

    public void stop() {
        this.transportServer.stop();
    }

}
