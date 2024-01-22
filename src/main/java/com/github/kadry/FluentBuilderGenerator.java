package com.github.kadry;

public class FluentBuilderGenerator {

    public static <T> T generate(Class<T> fluentBuilderInterface){
        return fluentBuilderInterface.cast(FluentBuilderProxy.newInstance(fluentBuilderInterface));
    }
}
