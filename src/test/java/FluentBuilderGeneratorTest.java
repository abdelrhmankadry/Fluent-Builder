import fixtures.TestBuilder;
import fixtures.TestTarget;

import org.junit.jupiter.api.Test;

import static com.github.kadry.FluentBuilderGenerator.generate;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
