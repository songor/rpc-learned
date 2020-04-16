package com.imooc.rpc.transport;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 处理请求
 */
public interface RequestHandler {

    void handle(InputStream in, OutputStream out);

}
