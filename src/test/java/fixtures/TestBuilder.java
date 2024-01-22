package fixtures;

import com.github.kadry.annotations.Setter;

public interface TestBuilder {

    @Setter(fieldName = "intField")
    TestBuilder withIntField(int intField);
    @Setter(fieldName = "stringField", value = "pre-defined-value")
    TestBuilder withStringField(String stringField);
    TestTarget build();
}
