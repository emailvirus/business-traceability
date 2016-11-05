package arrow.businesstraceability.control.bean.debug;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.ml.distance.DistanceMeasure;

public class CompanyDistanceMeasure implements DistanceMeasure {

    @Override
    public double compute(final double[] firstPoint, final double[] secondPoint) throws DimensionMismatchException {
        return 0;
    }

}
