package arrow.businesstraceability.debug;

import arrow.businesstraceability.cache.entity.Company_mst_suggest;
import arrow.framework.util.StringUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class Point.
 */
public class Point {

    /** The is visited. */
    private boolean isVisited;

    /** The name. */
    private String name;

    /** The is noise. */
    private boolean isNoise;

    /** The id. */
    private String id;

    /** The company mst. */
    private Company_mst_suggest companyMst;

    /**
     * Instantiates a new point.
     *
     * @param com the com
     */
    public Point(final Company_mst_suggest com) {
        // TODO Auto-generated constructor stub
        this.companyMst = com;
    }

    /**
     * Checks if is visited.
     *
     * @return true, if is visited
     */
    public boolean isVisited() {
        return this.isVisited;
    }

    /**
     * Sets the visited.
     *
     * @param isVisited the new visited
     */
    public void setVisited(final boolean isVisited) {
        this.isVisited = isVisited;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Checks if is noise.
     *
     * @return true, if is noise
     */
    public boolean isNoise() {
        return this.isNoise;
    }

    /**
     * Sets the noise.
     *
     * @param isNoise the new noise
     */
    public void setNoise(final boolean isNoise) {
        this.isNoise = isNoise;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return this.id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Gets the company mst.
     *
     * @return the company mst
     */
    public Company_mst_suggest getCompanyMst() {
        return this.companyMst;
    }

    /**
     * Sets the company mst.
     *
     * @param companyMst the new company mst
     */
    public void setCompanyMst(final Company_mst_suggest companyMst) {
        this.companyMst = companyMst;
    }

    /**
     * Prints the out.
     *
     * @return the string
     */
    public String printOut() {
        StringBuilder sbb = new StringBuilder();
        sbb.append(String.format("Code:%s | Name: %s | Url: %s | NameF: %s %n", this.companyMst.getCom_company_code(),
            this.companyMst.getCom_company_name(), this.companyMst.getCom_url(),
            this.companyMst.getCom_company_furigana()));
        return sbb.toString();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if ((obj == null) || !(obj instanceof Point)) {
            return false;
        }
        Point target = (Point) obj;
        if (target.getCompanyMst().getCom_company_code() == null) {
            return false;
        }

        return StringUtils.areEqual(this.getCompanyMst().getCom_company_code(),
            target.getCompanyMst().getCom_company_code());
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        if (this.companyMst == null) {
            return super.toString();
        }
        return this.companyMst.getCom_company_code();
    }
}
