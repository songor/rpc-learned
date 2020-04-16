package com.imooc.rpc.protocol;

import lombok.Data;

/**
 * RPC 响应
 */
@Data
public class Response {

    /**
     * 0 表示成功，否则表示失败
     */
    private Integer code = 0;

    private String message = "OK";

    private Object data;

}
