package arrow.businesstraceability.control.bean.debug;

import java.util.List;

import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

import arrow.businesstraceability.persistence.entity.Company_mst;
import arrow.businesstraceability.persistence.mapped.Company_mst_MAPPED;
import arrow.businesstraceability.persistence.mapped.Company_mst_MAPPED_;

@Repository
public abstract class CompanyCustomRepo
    extends arrow.framework.persistence.Abstract_entityRepositoryWrapper<Company_mst, Company_mst_MAPPED.Pk>
    implements CriteriaSupport<Company_mst> {

    public List<Company_mst> findAllActiveCompany() {
        return this.criteria().eq(Company_mst_MAPPED_.com_delete_flg, false).getResultList();
    }
}
