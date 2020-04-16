package com.imooc.rpc.demo;

import com.imooc.rpc.server.RpcServer;

public class Server {

    public static void main(String[] args) {
        RpcServer server = new RpcServer();
        server.register(CalculateService.class, new CalculateServiceImpl());
        server.start();
    }

}
