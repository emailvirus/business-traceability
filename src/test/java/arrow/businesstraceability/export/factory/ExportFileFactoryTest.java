package arrow.businesstraceability.export.factory;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import arrow.businesstraceability.constant.SummaryReportConstants;

public class ExportFileFactoryTest {
    @Test
    public void testFactoryMethod() {
        AbstractExportFactory output =
                ExportFileFactory.getFactory(SummaryReportConstants.PeriodicReport.PERIODIC_REPORT_TYPE);

        Assertions.assertThat(output).isNotNull();
        Assertions.assertThat(output).isInstanceOf(ExcelReportFactory.class);

    }

    @Test
    public void testFactoryMethod2() {
        AbstractExportFactory output =
                ExportFileFactory.getFactory(SummaryReportConstants.VisitTimeReport.VISIT_TIME_REPORT_TYPE);

        Assertions.assertThat(output).isNotNull();
        Assertions.assertThat(output).isInstanceOf(ExcelReportFactory.class);

    }

}
