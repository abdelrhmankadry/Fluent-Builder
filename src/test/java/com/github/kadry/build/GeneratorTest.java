package com.github.kadry.build;

import fixtures.TestEnum;
import fixtures.TestTarget;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class GeneratorTest {

    @Test
    void testGenerateRandomInt() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        int randomInt = (int) Generator.randomValue(Integer.class);
        boolean isRandom = false;
        int lastGeneratedValue = 0;
        for (int i = 0; i < 5; i++) {
            if (randomInt != lastGeneratedValue) {isRandom = true; break;}
            lastGeneratedValue = randomInt;
            randomInt = (int) Generator.randomValue(Integer.class);
        }
        assertTrue(isRandom);
    }

    @Test
    void testGenerateRandomString() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String randomString = (String) Generator.randomValue(String.class);
        assertNotEquals("", randomString);
    }

    @Test
    void testGenerateRandomDate() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Date randomDate = (Date) Generator.randomValue(Date.class);
        assertTrue(new Timestamp(randomDate.getTime()).after(Timestamp.valueOf("1999-12-30 23:59:59")));
    }

    @Test
    void testGetRandomEnum() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        TestEnum randomEnumValue= (TestEnum) Generator.randomValue(TestEnum.class);
        assertNotNull(TestEnum.valueOf(randomEnumValue.name()));
    }

    @Test
    void testCreateNewInstance() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        TestTarget testTarget = (TestTarget) Generator.randomValue(TestTarget.class);
        assertNotNull(testTarget);
        assertNull(testTarget.getStringField());
    }


}