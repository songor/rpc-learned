package com.imooc.rpc.server;

import com.imooc.rpc.common.util.ReflectionUtil;
import com.imooc.rpc.protocol.Request;
import com.imooc.rpc.protocol.ServiceDescriptor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理暴露的服务
 */
@Slf4j
public class ServiceManager {

    private Map<ServiceDescriptor, ServiceInstance> services;

    public ServiceManager() {
        this.services = new ConcurrentHashMap<>();
    }

    /**
     * 注册服务
     *
     * @param interfaceClass 暴露接口类
     * @param bean           实现 {@param interfaceClass} 的对象
     * @param <T>
     */
    public <T> void register(Class<T> interfaceClass, T bean) {
        Method[] methods = ReflectionUtil.getPublicMethods(interfaceClass);
        Arrays.stream(methods).forEach(method -> {
            ServiceDescriptor descriptor = ServiceDescriptor.from(interfaceClass, method);
            ServiceInstance instance = new ServiceInstance(bean, method);
            services.put(descriptor, instance);
            log.info("register service: {} {}", descriptor.getClassName(), descriptor.getMethodName());
        });
    }

    public ServiceInstance lookup(Request request) {
        ServiceDescriptor descriptor = request.getService();
        return services.get(descriptor);
    }

}
