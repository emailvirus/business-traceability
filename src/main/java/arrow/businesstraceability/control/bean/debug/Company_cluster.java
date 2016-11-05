// package arrow.businesstraceability.control.bean.debug;
//
// import java.io.Serializable;
//
// import org.apache.commons.math3.ml.clustering.Clusterable;
//
// import arrow.framework.util.StringUtils;
//
/// **
// * The Class Company_cluster.
// */
// public class Company_cluster implements Clusterable, Serializable {
//
// private String com_company_name;
//
// private String com_company_furigana;
//
// public String getCom_company_name() {
// return this.com_company_name;
// }
//
// public void setCom_company_name(final String com_company_name) {
// this.com_company_name = com_company_name;
// }
//
// public String getCom_company_furigana() {
// return this.com_company_furigana;
// }
//
// public void setCom_company_furigana(final String com_company_furigana) {
// this.com_company_furigana = com_company_furigana;
// }
//
// private String com_company_code;
//
// public String getCom_company_code() {
// return this.com_company_code;
// }
//
// public void setCom_company_code(final String com_company_code) {
// this.com_company_code = com_company_code;
// }
//
// public String getCom_customer_code() {
// return this.com_customer_code;
// }
//
// public void setCom_customer_code(final String com_customer_code) {
// this.com_customer_code = com_customer_code;
// }
//
// public String getCom_url() {
// return this.com_url;
// }
//
// public void setCom_url(final String com_url) {
// this.com_url = com_url;
// }
//
// public String getWorktype() {
// return this.worktype;
// }
//
// public void setWorktype(final String worktype) {
// this.worktype = worktype;
// }
//
// public short getListed() {
// return this.listed;
// }
//
// public void setListed(final short listed) {
// this.listed = listed;
// }
//
// private String com_customer_code;
//
// private String com_url;
//
// private String worktype;
//
// private short listed;
//
// /** The src point. */
// private Company_cluster srcPoint;
//
// private short com_indbig_code;
//
// public short getCom_limited_code() {
// return this.com_limited_code;
// }
//
// public void setCom_limited_code(final short com_limited_code) {
// this.com_limited_code = com_limited_code;
// }
//
// public short getCom_listed_code() {
// return this.com_listed_code;
// }
//
// public void setCom_listed_code(final short com_listed_code) {
// this.com_listed_code = com_listed_code;
// }
//
// public String getBig_name() {
// return this.big_name;
// }
//
// public void setBig_name(final String big_name) {
// this.big_name = big_name;
// }
//
// public String getCom_point_code() {
// return this.com_point_code;
// }
//
// public void setCom_point_code(final String com_point_code) {
// this.com_point_code = com_point_code;
// }
//
// public String getAdp_name() {
// return this.adp_name;
// }
//
// public void setAdp_name(final String adp_name) {
// this.adp_name = adp_name;
// }
//
// public short getCom_indbig_code() {
// return this.com_indbig_code;
// }
//
// private short com_limited_code;
//
// private short com_listed_code;
//
// private String big_name;
//
// private String com_point_code;
//
// private String adp_name;
//
// private int clusterIndex;
//
// private String furigana;
//
// private String name;
//
// private String domain;
//
// private String domainExt;
//
// public String getDomain() {
// return this.domain;
// }
//
// public String getDomainExt() {
// return this.domainExt;
// }
//
// public String getName() {
// return this.name;
// }
//
// public String getFurigana() {
// return this.furigana;
// }
//
// public int getClusterIndex() {
// return this.clusterIndex;
// }
//
// /**
// * Instantiates a new company_cluster.
// *
// * @param com_company_code the com_company_code
// */
// public Company_cluster(final String com_company_code) {
// this.com_company_code = com_company_code;
// }
//
// /**
// * Instantiates a new company_cluster.
// */
// public Company_cluster() {}
//
// /* (non-Javadoc)
// * @see org.apache.commons.math3.ml.clustering.Clusterable#getPoint()
// */
// @Override
// public double[] getPoint() {
// return new double[] {0.0, 0.0};
// }
//
//
// /**
// * Prints the out.
// *
// * @return the string
// */
// public String printOut() {
// String output = this.toString();
//
// if (this.srcPoint != null) {
// output += String.format("====> Source Point: %s", this.srcPoint.toString());
// }
// return output;
// }
//
//
// /* (non-Javadoc)
// * @see java.lang.Object#equals(java.lang.Object)
// */
// @Override
// public boolean equals(final Object obj) {
// if ((obj == null) || !(obj instanceof Company_cluster)) {
// return false;
// }
// Company_cluster target = (Company_cluster) obj;
// if ((this.getCom_company_code() == null) && (target.getCom_company_code() == null)) {
// return true;
// }
// return StringUtils.areEqual(this.getCom_company_code(), target.getCom_company_code());
// }
//
// /* (non-Javadoc)
// * @see java.lang.Object#toString()
// */
// @Override
// public String toString() {
// if (this.getCom_company_code() != null) {
// return String.format("Code: %s | Name: %s | NameF: %s | Url: %s %n", this.getCom_company_code(),
// this.getCom_company_name(), this.getCom_company_furigana(), this.getCom_url());
// }
// return super.toString();
// }
//
// /**
// * Sets the source point.
// *
// * @param current the new source point
// */
// public void setSourcePoint(final Company_cluster current) {
// this.srcPoint = current;
//
// }
//
// /**
// * Gets the src point.
// *
// * @return the src point
// */
// public Company_cluster getSrcPoint() {
// return this.srcPoint;
// }
//
// /**
// * Sets the src point.
// *
// * @param srcPoint the new src point
// */
// public void setSrcPoint(final Company_cluster srcPoint) {
// this.srcPoint = srcPoint;
// }
//
// public void setCom_indbig_code(final short comBigCode) {
// this.com_indbig_code = comBigCode;
// }
//
// public void setClusterIndex(final int clusterIndex) {
// this.clusterIndex = clusterIndex;
//
// }
//
// public void setFurigana(final String normalizeFurigana) {
// this.furigana = normalizeFurigana;
// }
//
// public void setName(final String convertToFullWidth) {
// this.name = convertToFullWidth;
// }
//
// public void setDomain(final String extractDomain) {
// this.domain = extractDomain;
// }
//
// public void setDomainExt(final String extractDomainExt) {
// this.domainExt = extractDomainExt;
// }
// }
