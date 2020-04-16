package com.imooc.rpc.client;

import com.imooc.rpc.codec.Decoder;
import com.imooc.rpc.codec.Encoder;
import com.imooc.rpc.codec.JsonDecoderImpl;
import com.imooc.rpc.codec.JsonEncoderImpl;
import com.imooc.rpc.protocol.Peer;
import com.imooc.rpc.transport.HttpTransportClientImpl;
import com.imooc.rpc.transport.TransportClient;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class RpcClientConfig {

    private Class<? extends TransportClient> transportClass = HttpTransportClientImpl.class;

    private Class<? extends Encoder> encoderClass = JsonEncoderImpl.class;

    private Class<? extends Decoder> decoderClass = JsonDecoderImpl.class;

    private Class<? extends TransportSelector> selectorClass = RandomTransportSelectorImpl.class;

    private List<Peer> peers = Arrays.asList(new Peer("127.0.0.1", 8080));

    private Integer count = 1;

}
