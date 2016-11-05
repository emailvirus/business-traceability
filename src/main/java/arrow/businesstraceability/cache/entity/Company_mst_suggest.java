package arrow.businesstraceability.cache.entity;

import java.io.Serializable;

import org.apache.commons.math3.ml.clustering.Clusterable;

import arrow.framework.util.StringUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class Company_mst_suggest.
 */
public class Company_mst_suggest implements Clusterable, Serializable {

    /** The com_company_name. */
    private String com_company_name;

    /** The com_company_furigana. */
    private String com_company_furigana;

    /**
     * Gets the com_company_name.
     *
     * @return the com_company_name
     */
    public String getCom_company_name() {
        return this.com_company_name;
    }

    /**
     * Sets the com_company_name.
     *
     * @param com_company_name the new com_company_name
     */
    public void setCom_company_name(final String com_company_name) {
        this.com_company_name = com_company_name;
    }

    /**
     * Gets the com_company_furigana.
     *
     * @return the com_company_furigana
     */
    public String getCom_company_furigana() {
        return this.com_company_furigana;
    }

    /**
     * Sets the com_company_furigana.
     *
     * @param com_company_furigana the new com_company_furigana
     */
    public void setCom_company_furigana(final String com_company_furigana) {
        this.com_company_furigana = com_company_furigana;
    }

    /** The com_company_code. */
    private String com_company_code;

    /**
     * Gets the com_company_code.
     *
     * @return the com_company_code
     */
    public String getCom_company_code() {
        return this.com_company_code;
    }

    /**
     * Sets the com_company_code.
     *
     * @param com_company_code the new com_company_code
     */
    public void setCom_company_code(final String com_company_code) {
        this.com_company_code = com_company_code;
    }

    /**
     * Gets the com_customer_code.
     *
     * @return the com_customer_code
     */
    public String getCom_customer_code() {
        return this.com_customer_code;
    }

    /**
     * Sets the com_customer_code.
     *
     * @param com_customer_code the new com_customer_code
     */
    public void setCom_customer_code(final String com_customer_code) {
        this.com_customer_code = com_customer_code;
    }

    /**
     * Gets the com_url.
     *
     * @return the com_url
     */
    public String getCom_url() {
        return this.com_url;
    }

    /**
     * Sets the com_url.
     *
     * @param com_url the new com_url
     */
    public void setCom_url(final String com_url) {
        this.com_url = com_url;
    }

    /**
     * Gets the worktype.
     *
     * @return the worktype
     */
    public String getWorktype() {
        return this.worktype;
    }

    /**
     * Sets the worktype.
     *
     * @param worktype the new worktype
     */
    public void setWorktype(final String worktype) {
        this.worktype = worktype;
    }

    /**
     * Gets the listed.
     *
     * @return the listed
     */
    public short getListed() {
        return this.listed;
    }

    /**
     * Sets the listed.
     *
     * @param listed the new listed
     */
    public void setListed(final short listed) {
        this.listed = listed;
    }

    /** The com_customer_code. */
    private String com_customer_code;

    /** The com_url. */
    private String com_url;

    /** The worktype. */
    private String worktype;

    /** The listed. */
    private short listed;

    /** The src point. */
    private Company_mst_suggest srcPoint;

    /** The com_indbig_code. */
    private short com_indbig_code;

    /**
     * Gets the com_limited_code.
     *
     * @return the com_limited_code
     */
    public short getCom_limited_code() {
        return this.com_limited_code;
    }

    /**
     * Sets the com_limited_code.
     *
     * @param com_limited_code the new com_limited_code
     */
    public void setCom_limited_code(final short com_limited_code) {
        this.com_limited_code = com_limited_code;
    }

    /**
     * Gets the com_listed_code.
     *
     * @return the com_listed_code
     */
    public short getCom_listed_code() {
        return this.com_listed_code;
    }

    /**
     * Sets the com_listed_code.
     *
     * @param com_listed_code the new com_listed_code
     */
    public void setCom_listed_code(final short com_listed_code) {
        this.com_listed_code = com_listed_code;
    }

    /**
     * Gets the big_name.
     *
     * @return the big_name
     */
    public String getBig_name() {
        return this.big_name;
    }

    /**
     * Sets the big_name.
     *
     * @param big_name the new big_name
     */
    public void setBig_name(final String big_name) {
        this.big_name = big_name;
    }

    /**
     * Gets the com_point_code.
     *
     * @return the com_point_code
     */
    public String getCom_point_code() {
        return this.com_point_code;
    }

    /**
     * Sets the com_point_code.
     *
     * @param com_point_code the new com_point_code
     */
    public void setCom_point_code(final String com_point_code) {
        this.com_point_code = com_point_code;
    }

    /**
     * Gets the adp_name.
     *
     * @return the adp_name
     */
    public String getAdp_name() {
        return this.adp_name;
    }

    /**
     * Sets the adp_name.
     *
     * @param adp_name the new adp_name
     */
    public void setAdp_name(final String adp_name) {
        this.adp_name = adp_name;
    }

    /**
     * Gets the com_indbig_code.
     *
     * @return the com_indbig_code
     */
    public short getCom_indbig_code() {
        return this.com_indbig_code;
    }

    /** The com_limited_code. */
    private short com_limited_code;

    /** The com_listed_code. */
    private short com_listed_code;

    /** The big_name. */
    private String big_name;

    /** The com_point_code. */
    private String com_point_code;

    /** The adp_name. */
    private String adp_name;

    /** The cluster index. */
    private int clusterIndex;

    /** The furigana. */
    private String furigana;

    /** The name. */
    private String name;

    /** The domain. */
    private String domain;

    /** The domain ext. */
    private String domainExt;

    /**
     * Gets the domain.
     *
     * @return the domain
     */
    public String getDomain() {
        return this.domain;
    }

    /**
     * Gets the domain ext.
     *
     * @return the domain ext
     */
    public String getDomainExt() {
        return this.domainExt;
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
     * Gets the furigana.
     *
     * @return the furigana
     */
    public String getFurigana() {
        return this.furigana;
    }

    /**
     * Gets the cluster index.
     *
     * @return the cluster index
     */
    public int getClusterIndex() {
        return this.clusterIndex;
    }


    /**
     * Instantiates a new company_mst_suggest.
     *
     * @param com_company_code the com_company_code
     */
    public Company_mst_suggest(final String com_company_code) {
        this.com_company_code = com_company_code;
    }


    /**
     * Instantiates a new company_mst_suggest.
     */
    public Company_mst_suggest() {}

    /* (non-Javadoc)
     * @see org.apache.commons.math3.ml.clustering.Clusterable#getPoint()
     */
    @Override
    public double[] getPoint() {
        return new double[] {0.0, 0.0};
    }


    /**
     * Prints the out.
     *
     * @return the string
     */
    public String printOut() {
        String output = this.toString();

        if (this.srcPoint != null) {
            output += String.format("====> Source Point: %s", this.srcPoint.toString());
        }
        return output;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if ((obj == null) || !(obj instanceof Company_mst_suggest)) {
            return false;
        }
        Company_mst_suggest target = (Company_mst_suggest) obj;
        if ((this.getCom_company_code() == null) && (target.getCom_company_code() == null)) {
            return true;
        }
        return StringUtils.areEqual(this.getCom_company_code(), target.getCom_company_code());
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        if (this.getCom_company_code() != null) {
            return String.format("Code: %s | Name: %s | NameF: %s | Url: %s %n", this.getCom_company_code(),
                this.getCom_company_name(), this.getCom_company_furigana(), this.getCom_url());
        }
        return super.toString();
    }

    /**
     * Sets the source point.
     *
     * @param current the new source point
     */
    public void setSourcePoint(final Company_mst_suggest current) {
        this.srcPoint = current;

    }

    /**
     * Gets the src point.
     *
     * @return the src point
     */
    public Company_mst_suggest getSrcPoint() {
        return this.srcPoint;
    }

    /**
     * Sets the src point.
     *
     * @param srcPoint the new src point
     */
    public void setSrcPoint(final Company_mst_suggest srcPoint) {
        this.srcPoint = srcPoint;
    }

    /**
     * Sets the com_indbig_code.
     *
     * @param comBigCode the new com_indbig_code
     */
    public void setCom_indbig_code(final short comBigCode) {
        this.com_indbig_code = comBigCode;
    }

    /**
     * Sets the cluster index.
     *
     * @param clusterIndex the new cluster index
     */
    public void setClusterIndex(final int clusterIndex) {
        this.clusterIndex = clusterIndex;

    }

    /**
     * Sets the furigana.
     *
     * @param normalizeFurigana the new furigana
     */
    public void setFurigana(final String normalizeFurigana) {
        this.furigana = normalizeFurigana;
    }

    /**
     * Sets the name.
     *
     * @param convertToFullWidth the new name
     */
    public void setName(final String convertToFullWidth) {
        this.name = convertToFullWidth;
    }

    /**
     * Sets the domain.
     *
     * @param extractDomain the new domain
     */
    public void setDomain(final String extractDomain) {
        this.domain = extractDomain;
    }

    /**
     * Sets the domain ext.
     *
     * @param extractDomainExt the new domain ext
     */
    public void setDomainExt(final String extractDomainExt) {
        this.domainExt = extractDomainExt;
    }
}
