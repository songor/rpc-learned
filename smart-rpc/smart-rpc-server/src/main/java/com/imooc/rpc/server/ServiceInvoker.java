package com.imooc.rpc.server;

import com.imooc.rpc.common.util.ReflectionUtil;
import com.imooc.rpc.protocol.Request;

/**
 * 调用服务
 */
public class ServiceInvoker {

    public Object invoke(ServiceInstance instance, Request request) {
        return ReflectionUtil.invoke(instance.getTarget(), instance.getMethod(), request.getParams());
    }

}
