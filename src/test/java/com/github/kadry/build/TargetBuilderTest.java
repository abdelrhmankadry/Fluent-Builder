package com.github.kadry.build;

import com.github.kadry.build.TargetBuilder;
import com.github.kadry.interpret.model.BuildMethod;
import com.github.kadry.interpret.model.Interpretation;
import com.github.kadry.interpret.model.SetMethod;
import fixtures.TestBuilder;
import fixtures.TestTarget;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TargetBuilderTest {

    @Test
    void testTargetBuilding() throws NoSuchMethodException {
        Interpretation interpretation = getTestInterpretation();
        TargetBuilder targetBuilder = new TargetBuilder(interpretation);
        Object proxyObject = new Object();
        Object returnValue = targetBuilder.methodInvocation("withIntField", 10, proxyObject);
        assertEquals(proxyObject, returnValue);

        TestTarget builtTarget = (TestTarget) targetBuilder.methodInvocation("build", null, proxyObject);
        assertEquals(10, builtTarget.getIntField());
    }


    @Test
    void testTargetBuildingWithAutoGeneratedFields() throws NoSuchMethodException {
        Interpretation interpretation = getTestInterpretation();
        TargetBuilder targetBuilder = new TargetBuilder(interpretation);
        Object proxyObject = new Object();
        boolean isRandom = false;
        int lastGeneratedValue = 0;
        for (int i = 0; i < 5; i++) {
            TestTarget builtTarget = (TestTarget) targetBuilder.methodInvocation("build", null, proxyObject);
            isRandom = isRandom || builtTarget.getIntField() != lastGeneratedValue; // checking randomness
            lastGeneratedValue = builtTarget.getIntField();
        }
        assertTrue(isRandom);
    }

    private Interpretation getTestInterpretation() {
        SetMethod setMethod = new SetMethod("withIntField"
                , "intField", null, Integer.class, null);
        BuildMethod buildMethod = new BuildMethod("build", null);
        Map<String, Class<?>> fields = new HashMap<>();
        fields.put("intField", Integer.class);

        return new Interpretation(
                Collections.singletonList(setMethod),
                Collections.singletonList(buildMethod),
                fields,
                TestBuilder.class,
                TestTarget.class
        );
    }
}
