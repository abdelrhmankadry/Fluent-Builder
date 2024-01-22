package com.github.kadry.build;

import com.github.kadry.interpret.model.Interpretation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TargetBuilder {

    private final Interpretation interpretation;
    private final Map<String, Object> fieldValue;
    public TargetBuilder(Interpretation interpretation) {
        this.interpretation = interpretation;
        this.fieldValue = new HashMap<>();
    }

    public Object methodInvocation(String methodName, Object parameter, Object proxy) throws NoSuchMethodException {
        if (interpretation.isSetMethod(methodName)){
            fieldValue.put(interpretation.getFieldName(methodName), parameter);
            return proxy;
        } else if(interpretation.isBuildMethod(methodName)){
            return build(methodName);
        } else
            throw new NoSuchMethodException();
    }

    private Object build(String methodName) {
        generateUnspecifiedFieldsValues();
        try {
            Object builtObject = this.interpretation.getTargetType().getDeclaredConstructor().newInstance();
            fieldValue.keySet().forEach(fieldName ->{
                try {
                    Field field = this.interpretation.getTargetType().getDeclaredField(fieldName);
                    field.setAccessible(true);
                    field.set(builtObject, fieldValue.get(fieldName));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            });
            return builtObject;
        } catch (InstantiationException | IllegalAccessException
                 | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateUnspecifiedFieldsValues(){
       unspecifiedFields().forEach(fieldsName ->
       {
           try {
               if (interpretation.getFieldValue(fieldsName)!= null)
                   fieldValue.put(fieldsName, interpretation.getFieldValue(fieldsName));
               else
                    fieldValue.put(fieldsName, Generator.randomValue(interpretation.getFieldType(fieldsName)));
           } catch (InvocationTargetException e) {
               throw new RuntimeException(e);
           } catch (NoSuchMethodException e) {
               throw new RuntimeException(e);
           } catch (InstantiationException e) {
               throw new RuntimeException(e);
           } catch (IllegalAccessException e) {
               throw new RuntimeException(e);
           }
       });

    }

    private List<String> unspecifiedFields() {
        List<String> fieldsSet = interpretation.getAllFieldsNames();
        fieldsSet.removeAll(fieldValue.keySet());
        return fieldsSet;
    }
}
