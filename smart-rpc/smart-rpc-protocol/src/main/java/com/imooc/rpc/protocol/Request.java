package com.imooc.rpc.protocol;

import lombok.Data;

/**
 * RPC 请求
 */
@Data
public class Request {

    private ServiceDescriptor service;

    private Object[] params;

}
