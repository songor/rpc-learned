package com.imooc.rpc.codec;

import com.alibaba.fastjson.JSON;

public class JsonEncoderImpl implements Encoder {

    @Override
    public byte[] encode(Object obj) {
        return JSON.toJSONBytes(obj);
    }

}
