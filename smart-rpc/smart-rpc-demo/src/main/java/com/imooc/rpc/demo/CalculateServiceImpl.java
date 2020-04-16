package com.imooc.rpc.demo;

public class CalculateServiceImpl implements CalculateService {

    @Override
    public Integer add(Integer a, Integer b) {
        return a + b;
    }

    @Override
    public Integer sub(Integer a, Integer b) {
        return a - b;
    }

}
