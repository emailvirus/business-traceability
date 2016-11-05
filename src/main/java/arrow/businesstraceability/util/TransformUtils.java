package arrow.businesstraceability.util;

import arrow.businesstraceability.cache.entity.Company_mst_suggest;
import arrow.businesstraceability.persistence.entity.Company_mst;
import arrow.framework.faces.util.BeanCopier;

// TODO: Auto-generated Javadoc
/**
 * The Class TransformUtils.
 */
public class TransformUtils {

    /**
     * Transform.
     *
     * @param companySelected the company selected
     * @return the company_mst_suggest
     */
    public static Company_mst_suggest transform(final Company_mst companySelected) {
        if (null == companySelected) {
            return null;
        }
        Company_mst_suggest compSuggest = new Company_mst_suggest(companySelected.getCom_company_code());
        BeanCopier.copy(companySelected, compSuggest);
        compSuggest.setWorktype(companySelected.getAddresspoint_mst().getAdp_code());
        compSuggest.setListed(companySelected.getCom_listed_code());
        return compSuggest;
    }

    /**
     * Transform to.
     *
     * @param company the company
     * @return the company_cluster
     */
    // public static Company_cluster transformTo(final Company_mst company) {
    // if (company == null) {
    // return null;
    // }
    // Company_cluster cluster = new Company_cluster(company.getCom_company_code());
    // BeanCopier.copy(company, cluster);
    // if (!StringUtils.isEmpty(cluster.getCom_url())) {
    // cluster.setCom_url(StringUtils.removeUrlToken(cluster.getCom_url()));
    // int indexSpecial = cluster.getCom_url().indexOf("/");
    // if (indexSpecial > 0) {
    // cluster.setCom_url(cluster.getCom_url().substring(0, indexSpecial));
    // }
    // }
    // return cluster;
    // }

}
