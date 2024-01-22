package com.github.kadry.interpret.model;

import com.github.kadry.annotations.Setter;

import java.util.List;

public class BuildMethod {
    private String methodName;
    private List<Setter> setters;

    public BuildMethod(String name, List<Setter> setters) {
        this.methodName = name;
        this.setters = setters;
    }

    public BuildMethod() {
    }


    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<Setter> getSetters() {
        return setters;
    }

    public void setSetters(List<Setter> setters) {
        this.setters = setters;
    }
}
