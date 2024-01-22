package com.github.kadry.interpret;

import com.github.kadry.annotations.Builder;
import com.github.kadry.annotations.Setter;
import com.github.kadry.interpret.model.BuildMethod;
import com.github.kadry.interpret.model.Interpretation;
import com.github.kadry.interpret.model.SetMethod;
import sun.plugin.dom.exception.InvalidStateException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Interpreter {

    public static Interpretation interpret(Class<?> fluentBuilderInterface){
        Class<?> targetType = extractTargetType(fluentBuilderInterface);
        List<SetMethod> setMethods = extractSetMethods(fluentBuilderInterface);
        List<BuildMethod> buildMethods = extractBuildMethods(fluentBuilderInterface);
        Map<String, Class<?>> fields = extractFieldsOfTarget(fluentBuilderInterface);
        return new Interpretation(
                setMethods,
                buildMethods,
                fields,
                fluentBuilderInterface,
                targetType);
    }

    private static Map<String, Class<?>> extractFieldsOfTarget(Class<?> fluentBuilderInterface) {
        return Arrays.stream(extractTargetType(fluentBuilderInterface).getDeclaredFields())
                .collect(Collectors.toMap(Field::getName, Field::getType));
    }

    private static List<BuildMethod> extractBuildMethods(Class<?> fluentBuilderInterface) {
        return Arrays.stream(fluentBuilderInterface.getDeclaredMethods())
                .filter(method -> !method.getReturnType().equals(fluentBuilderInterface))
                .map(method -> {
                    BuildMethod buildMethod = new BuildMethod();
                    buildMethod.setMethodName(method.getName());
                    if(method.getAnnotation(Builder.class) != null){
                        buildMethod.setSetters(Arrays.asList(method.getAnnotation(Builder.class).setters()));
                        buildMethod.setMethodName(method.getName());
                    }
                    return buildMethod;
                        }
                ).collect(Collectors.toList());
    }

    private static List<SetMethod> extractSetMethods(Class<?> fluentBuilderInterface) {
        return Arrays.stream(fluentBuilderInterface.getDeclaredMethods())
                .filter(method -> method.getAnnotation(Setter.class) != null)
                .map(method -> new SetMethod(
                        method.getName(),
                        method.getAnnotation(Setter.class).fieldName(),
                        method.getAnnotation(Setter.class).value(),
                        method.getParameters()[0].getClass(),
                        method.getAnnotation(Setter.class).pattern()
                        )).collect(Collectors.toList());
    }

    private static Class<?> extractTargetType(Class<?> fluentBuilderInterface) {
        return Arrays.stream(fluentBuilderInterface.getMethods())
                .filter(method -> !method.getReturnType().equals(fluentBuilderInterface))
                .findFirst()
                .orElseThrow(()-> new InvalidStateException("Fluent Builder interface contains no build function"))
                .getReturnType();
    }
}
