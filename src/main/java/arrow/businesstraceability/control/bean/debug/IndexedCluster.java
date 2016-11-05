package arrow.businesstraceability.control.bean.debug;

import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.ml.clustering.Clusterable;

public class IndexedCluster<T extends Clusterable> extends Cluster<T> {
    private int index;

    public IndexedCluster(final int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(final int index) {
        this.index = index;
    }


    public int getConfidentLevel() {
        return confidentLevel;
    }

    public void setConfidentLevel(int confidentLevel) {
        this.confidentLevel = confidentLevel;
    }


    private int confidentLevel;

}
