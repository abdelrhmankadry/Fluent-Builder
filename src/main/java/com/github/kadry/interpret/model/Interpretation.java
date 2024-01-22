package com.github.kadry.interpret.model;

import java.util.*;

public class Interpretation {

    private List<SetMethod> setMethods;
    private List<BuildMethod> buildMethods;
    private Map<String, Class<?>> fields;
    private Class<?> fluentBuilderInterface;
    private Class<?> targetType;

    public Interpretation(List<SetMethod> setMethods,
                          List<BuildMethod> buildMethods,
                          Map<String, Class<?>> fields,
                          Class<?> fluentBuilderInterface,
                          Class<?> targetType) {
        this.setMethods = setMethods;
        this.buildMethods = buildMethods;
        this.fields = fields;
        this.fluentBuilderInterface = fluentBuilderInterface;
        this.targetType = targetType;
    }

    public boolean isSetMethod(String methodName) {
        return setMethods.stream().anyMatch(setMethod -> setMethod.getMethodName().equals(methodName));
    }

    public boolean isBuildMethod(String methodName) {
        return buildMethods.stream().anyMatch(buildMethod -> buildMethod.getMethodName().equals(methodName));
    }

    public Class<?> getFieldType(String fieldName) {
        return fields.get(fieldName);
    }

    public String getFieldName(String methodName) throws NoSuchMethodException {
        return setMethods.stream()
                .filter(setMethod -> setMethod.getMethodName().equals(methodName))
                .findFirst()
                .orElseThrow(NoSuchMethodException::new)
                .getFieldName();
    }

    public Object getFieldValue(String fieldName){
        // TODO: cast returned value.
        return setMethods.stream().filter(setMethod -> setMethod.getFieldName().equals(fieldName))
                .findFirst()
                .orElse(new SetMethod())
                .getValue();
    }
    public List<String> getAllFieldsNames() {
        return new ArrayList<>(fields.keySet());
    }

    public Class<?> getTargetType() {
        return this.targetType;
    }
}
