package com.imooc.rpc.codec;

import com.alibaba.fastjson.JSON;

public class JsonDecoderImpl implements Decoder {

    @Override
    public <T> T decode(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }

}
