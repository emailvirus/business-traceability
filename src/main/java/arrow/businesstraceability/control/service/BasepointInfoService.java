package arrow.businesstraceability.control.service;

import java.util.List;

import javax.inject.Named;

import arrow.businesstraceability.base.AbstractService;
import arrow.businesstraceability.persistence.entity.Basepoint_info;


/**
 * The Class BasepointInfoService.
 */
@Named
public class BasepointInfoService extends AbstractService {

    /**
     * Gets the basepoint info by company code.
     *
     * @param companyCode the company code
     * @return the basepoint info by company code
     */
    public List<Basepoint_info> getBasepointInfoByCompanyCode(final String companyCode) {
        return super.emMain
                .createQuery(
                        "FROM Basepoint_info WHERE bas_delete_flg is FALSE "
                                + "AND bas_company_code = :companyCode ORDER BY bas_point_code", Basepoint_info.class)
                                .setParameter("companyCode", companyCode).getResultList();
    }

    /**
     * Gets the all basepoint info by company code.
     *
     * @param companyCode the company code
     * @return the all basepoint info by company code
     */
    public List<Basepoint_info> getAllBasepointInfoByCompanyCode(final String companyCode) {
        return super.emMain
                .createQuery("FROM Basepoint_info WHERE bas_company_code = :companyCode ORDER BY bas_point_code",
                        Basepoint_info.class).setParameter("companyCode", companyCode).getResultList();
    }
}
