package com.imooc.rpc.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 网络传输端点
 */
@Data
@AllArgsConstructor
public class Peer {

    private String host;

    private Integer port;

}
