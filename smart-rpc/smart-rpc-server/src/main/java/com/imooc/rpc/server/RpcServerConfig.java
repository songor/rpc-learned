package com.imooc.rpc.server;

import com.imooc.rpc.codec.Decoder;
import com.imooc.rpc.codec.Encoder;
import com.imooc.rpc.codec.JsonDecoderImpl;
import com.imooc.rpc.codec.JsonEncoderImpl;
import com.imooc.rpc.transport.HttpTransportServerImpl;
import com.imooc.rpc.transport.TransportServer;
import lombok.Data;

@Data
public class RpcServerConfig {

    private Class<? extends TransportServer> transportClass = HttpTransportServerImpl.class;

    private Class<? extends Encoder> encoderClass = JsonEncoderImpl.class;

    private Class<? extends Decoder> decoderClass = JsonDecoderImpl.class;

    private Integer port = 8080;

}
