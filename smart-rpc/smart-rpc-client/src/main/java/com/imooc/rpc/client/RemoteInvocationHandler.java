package com.imooc.rpc.client;

import com.imooc.rpc.codec.Decoder;
import com.imooc.rpc.codec.Encoder;
import com.imooc.rpc.protocol.Request;
import com.imooc.rpc.protocol.Response;
import com.imooc.rpc.protocol.ServiceDescriptor;
import com.imooc.rpc.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class RemoteInvocationHandler implements InvocationHandler {

    private Class clazz;

    private Encoder encoder;

    private Decoder decoder;

    private TransportSelector selector;

    public RemoteInvocationHandler(Class clazz, Encoder encoder, Decoder decoder, TransportSelector selector) {
        this.clazz = clazz;
        this.encoder = encoder;
        this.decoder = decoder;
        this.selector = selector;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        Request request = new Request();
        request.setService(ServiceDescriptor.from(this.clazz, method));
        request.setParams(args);
        Response response = invokeRemote(request);
        if (response == null || 0 != response.getCode()) {
            throw new IllegalStateException("Fail to invoke remote server, " + response);
        }
        return response.getData();
    }

    private Response invokeRemote(Request request) {
        Response response;
        TransportClient client = null;
        try {
            client = this.selector.select();
            byte[] outBytes = this.encoder.encode(request);
            InputStream in = client.write(new ByteArrayInputStream(outBytes));
            byte[] inBytes = IOUtils.readFully(in, in.available());
            response = this.decoder.decode(inBytes, Response.class);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            response = new Response();
            response.setCode(1);
            response.setMessage("Rpc client invoke fail, " + e.getMessage());
        } finally {
            if (client != null) {
                this.selector.release(client);
            }
        }
        return response;
    }

}
