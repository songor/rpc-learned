package com.imooc.rpc.server;

import com.imooc.rpc.common.util.ReflectionUtil;
import com.imooc.rpc.protocol.Request;
import com.imooc.rpc.protocol.ServiceDescriptor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ServiceManagerTest {

    private ServiceManager manager;

    @Before
    public void init() {
        manager = new ServiceManager();
        register();
    }

    private void register() {
        HelloService helloService = new HelloServiceImpl();
        manager.register(HelloService.class, helloService);
    }

    @Test
    public void lookup() {
        ServiceDescriptor descriptor = ServiceDescriptor
                .from(HelloService.class, ReflectionUtil.getPublicMethods(HelloService.class)[0]);
        Request request = new Request();
        request.setService(descriptor);
        ServiceInstance instance = manager.lookup(request);
        assertNotNull(instance);
    }

}