# Fluent Builder

Fluent Builder is a lightweight Java library that simplifies the process of creating objects with random values using the builder pattern and fluent interface pattern. With Fluent Builder, developers only need to define an interface file that describes how a function signature relates to some property of a class and provide default values if needed. The library takes care of the rest, allowing for easy and concise object creation.

## Getting Started

To get started with Fluent Builder, follow these simple steps:

1. Include the Fluent Builder library in your project.

2. Define an interface that describes the properties of the class you want to build. Annotate the interface methods with the `@Setter` annotation to specify the field name and default values if needed.

3. Use the `generate` method to create an instance of the builder for the defined interface.

4. Chain method calls using the fluent interface pattern to set the desired values.

5. Call the `build` method to obtain the final object.

## Example

Here's a simple example to illustrate how Fluent Builder works:

```java
public interface TestBuilder {

    @Setter(fieldName = "intField")
    TestBuilder withIntField(int intField);

    @Setter(fieldName = "stringField", value = "pre-defined-value")
    TestBuilder withStringField(String stringField);

    TestTarget build();
}

public class FluentBuilderGeneratorTest {

    @Test
    void generateBuilderTest() {
        TestBuilder testBuilder = generate(TestBuilder.class);

        TestTarget testTarget = testBuilder
                .withIntField(10)
                .build();

        assertEquals(10, testTarget.getIntField());
        assertEquals("pre-defined-value", testTarget.getStringField());
    }
}

public class TestTarget {

    private int intField;
    private String stringField;

    public TestTarget() {}

    public int getIntField() {
        return intField;
    }

    public void setIntField(int intField) {
        this.intField = intField;
    }

    public String getStringField() {
        return stringField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }
}
```

In this example, the `TestBuilder` interface defines methods annotated with `@Setter`, specifying the field names and default values. The `FluentBuilderGeneratorTest` demonstrates how to generate a builder and use it to create an instance of the `TestTarget` class with specified values.

