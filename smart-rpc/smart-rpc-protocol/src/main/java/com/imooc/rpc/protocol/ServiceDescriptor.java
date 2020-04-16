package com.imooc.rpc.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * 描述服务
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDescriptor {

    private String className;

    private String methodName;

    private String returnType;

    private String[] paramTypes;

    public static ServiceDescriptor from(Class clazz, Method method) {
        ServiceDescriptor descriptor = new ServiceDescriptor();
        descriptor.setClassName(clazz.getName());
        descriptor.setMethodName(method.getName());
        descriptor.setReturnType(method.getReturnType().getName());
        String[] paramTypes = Arrays.stream(method.getParameterTypes())
                .map(Class::getName).toArray(String[]::new);
        descriptor.setParamTypes(paramTypes);
        return descriptor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServiceDescriptor that = (ServiceDescriptor) o;
        return className.equals(that.className) &&
                methodName.equals(that.methodName) &&
                returnType.equals(that.returnType) &&
                Arrays.equals(paramTypes, that.paramTypes);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(className, methodName, returnType);
        result = 31 * result + Arrays.hashCode(paramTypes);
        return result;
    }

}
