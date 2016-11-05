package arrow.framework.util;

import org.assertj.core.api.Assertions;
import org.junit.Test;


public class DateUtilsTest {

    @Test
    public void testFormatJapaneseFullDateTime() {

        String ExpectedDate = "2016年1月12日 0時00分00秒";
        String date = DateUtils.formatJapaneseFullDateTime(DateUtils.buildDate(12, 1, 2016));
        Assertions.assertThat(date).isEqualTo(ExpectedDate);

    }
}
