package com.github.kadry.interpret.model;

public class SetMethod {

    private String methodName;
    private String fieldName;
    private String value;
    private Class<?> parameterType;
    private String pattern;

    public SetMethod(String methodName, String fieldName, String value, Class<?> parameterType, String pattern) {
        this.methodName = methodName;
        this.fieldName = fieldName;
        this.value = value;
        this.parameterType = parameterType;
        this.pattern = pattern;
    }

    public SetMethod() {
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Class<?> getParameterType() {
        return parameterType;
    }

    public void setParameterType(Class<?> parameterType) {
        this.parameterType = parameterType;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
