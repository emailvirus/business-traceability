//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import arrow.businesstraceability.control.service.BasepointInfoService;
import arrow.businesstraceability.persistence.dto.Company_mst_DTO;
import arrow.businesstraceability.persistence.mapped.Company_mst_MAPPED;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;
import arrow.framework.util.StringUtils;
import arrow.framework.util.cdi.Instance;
import arrow.framework.util.collections.CollectionUtils;
import arrow.framework.util.i18n.Messages;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Company_mst extends Company_mst_MAPPED {

    public Company_mst() {
    }

    public Company_mst(final java.lang.String com_company_code) {
        super(com_company_code);
    }

    public static Company_mst find(final java.lang.String com_company_code) {
        return EmLocator.getEm().find(Company_mst.class, new Company_mst.Pk(com_company_code));
    }

    public Company_mst_DTO getDto() {
        return DtoUtils.createDto(this, Company_mst_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @Transient
    private String allBranch;

    @Transient
    private String allBranches;

    public String getAllBranch() {
        if (CollectionUtils.isEmpty(this.getListBasepointInfos())) {
            this.allBranches = StringUtils.EMPTY_STRING;
        }
        else {
            final StringBuilder sb = new StringBuilder();
            sb.append(this.getListBasepointInfos().get(0).getAddresspoint_mst().getAdp_name());
            for (int index = 1; index < this.getListBasepointInfos().size(); index++) {
                sb.append(", ").append(this.getBasepoint_infos().get(index).getAddresspoint_mst().getAdp_name());
            }
            this.allBranches = sb.toString();
        }
        return this.allBranches;
    }

    public List<Basepoint_info> getListBasepointInfos() {
        if (StringUtils.isEmpty(this.getCom_company_code())) {
            return Collections.emptyList();
        }
        return Instance.get(BasepointInfoService.class).getBasepointInfoByCompanyCode(this.getCom_company_code());
    }

    public void setAllBranch(final String allBranch) {
        this.allBranches = allBranch;
    }

    public List<Basepoint_info> getBasepoint_infos() {
        return this.basepoint_infos;
    }

    public void setBasepoint_infos(final List<Basepoint_info> basepoint_infos) {
        this.basepoint_infos = basepoint_infos;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company_mst")
    @JsonIgnore
    private List<Basepoint_info> basepoint_infos = new ArrayList<>();

    public String getExtendComName() {
        String com_name = super.getCom_company_name();
        short com_limited_code = super.getCom_limited_code();
        if (com_limited_code == 0) {
            return com_name;
        }
        else if (com_limited_code == 1) {
            return "(" + Messages.get("joint_stock") + ") " + com_name;
        }
        else if (com_limited_code == 2) {
            return com_name + " (" + Messages.get("joint_stock") + ")";
        }
        else if (com_limited_code == 3) {
            return "(" + Messages.get("private_limited") + ") " + com_name;
        }
        else if (com_limited_code == 4) {
            return com_name + " (" + Messages.get("private_limited") + ")";
        }
        return com_name;
    }

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}