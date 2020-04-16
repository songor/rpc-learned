package com.imooc.rpc.demo;

import com.imooc.rpc.client.RpcClient;

public class Client {

    public static void main(String[] args) {
        RpcClient client = new RpcClient();
        CalculateService service = client.getProxy(CalculateService.class);
        service.add(1, 2);
        service.sub(1, 2);
    }

}
