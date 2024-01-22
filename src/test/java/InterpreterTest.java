import com.github.kadry.interpret.model.Interpretation;
import com.github.kadry.interpret.Interpreter;
import fixtures.TestBuilder;
import fixtures.TestTarget;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InterpreterTest {

    @Test
    void testInterpreter() throws NoSuchMethodException {
        Interpretation interpretation = Interpreter.interpret(TestBuilder.class);
        assertEquals(interpretation.getFieldName("withIntField"), "intField");
        assertTrue(interpretation.isSetMethod("withIntField"));
        assertEquals(interpretation.getTargetType(), TestTarget.class);
        assertTrue(interpretation.isBuildMethod("build"));
        assertEquals(interpretation.getAllFieldsNames().size(), 2);
        assertArrayEquals(interpretation.getAllFieldsNames().toArray(), new String[]{"stringField", "intField"});
    }
}
