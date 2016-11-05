package arrow.businesstraceability.util;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class ArrowStringUtilsTest {

    @Test
    public void testIsNumeric_OK() throws Exception {
        String input = "1234";
        boolean output = ArrowStringUtils.isNumeric(input);
        Assertions.assertThat(output).isTrue();
    }

    @Test
    public void testIsNumeric_ShouldFalse() throws Exception {
        String input = "a1234";
        boolean output = ArrowStringUtils.isNumeric(input);
        Assertions.assertThat(output).isFalse();
    }

    @Test
    public void testIsNumeric_checknull_shouldFalse() throws Exception {
        String input = null;
        boolean output = ArrowStringUtils.isNumeric(input);
        Assertions.assertThat(output).isFalse();
    }


}
