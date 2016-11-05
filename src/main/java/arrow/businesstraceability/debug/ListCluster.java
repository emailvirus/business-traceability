package arrow.businesstraceability.debug;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class ListCluster.
 */
public class ListCluster {

    /** The index. */
    private int index;

    /** The list point. */
    private final List<Point> listPoint;

    /**
     * Gets the index.
     *
     * @return the index
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * Sets the index.
     *
     * @param index the new index
     */
    public void setIndex(final int index) {
        this.index = index;
    }

    /**
     * Gets the list point.
     *
     * @return the list point
     */
    public List<Point> getListPoint() {
        return this.listPoint;
    }

    /**
     * Instantiates a new list cluster.
     */
    public ListCluster() {
        this.listPoint = new ArrayList<Point>();
    }

    /**
     * Prints the out.
     *
     * @return the string
     */
    public String printOut() {
        StringBuilder sbb = new StringBuilder();
        sbb.append(String.format("--------------- Cluster %d  ----------%n", this.index));
        sbb.append(String.format("Number of nodes: %d", this.listPoint.size()));

        sbb.append("----------------");
        this.listPoint.forEach(point -> sbb.append(point.printOut()));
        return sbb.toString();
    }

}
