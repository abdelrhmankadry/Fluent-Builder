package com.github.kadry;

import com.github.kadry.build.TargetBuilder;
import com.github.kadry.interpret.Interpreter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class FluentBuilderProxy implements InvocationHandler {

    private final TargetBuilder targetBuilder;
    private FluentBuilderProxy(Class<?> fluentBuilderInterface) {
        this.targetBuilder = new TargetBuilder(Interpreter.interpret(fluentBuilderInterface));
    }

    public static Object newInstance(Class<?> fluentBuilderInterface){
        return Proxy.newProxyInstance(
                fluentBuilderInterface.getClassLoader(),
                new Class[] {fluentBuilderInterface},
                new FluentBuilderProxy(fluentBuilderInterface)
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(args != null)
            return targetBuilder.methodInvocation(method.getName(), args[0], proxy);
        else
            return targetBuilder.methodInvocation(method.getName(), null, proxy);
    }
}
