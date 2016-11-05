//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import arrow.businesstraceability.constant.Constants;
import arrow.businesstraceability.control.bean.common.CommonSelectorBean;
import arrow.businesstraceability.persistence.dto.Acc_com_credit_DTO;
import arrow.businesstraceability.persistence.mapped.Acc_com_credit_MAPPED;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;
import arrow.framework.util.cdi.Instance;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Acc_com_credit extends Acc_com_credit_MAPPED {

    public Acc_com_credit() {
    }

    public Acc_com_credit(final int id_credit) {
        super(id_credit);
    }

    public static Acc_com_credit find(final int id_credit) {
        return EmLocator.getEm().find(Acc_com_credit.class, new Acc_com_credit.Pk(id_credit));
    }

    public Acc_com_credit_DTO getDto() {
        return DtoUtils.createDto(this, Acc_com_credit_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "AC_MIDDLE_AUTHORIZE", referencedColumnName = "EMP_CODE", insertable = false,
        updatable = false)})
    protected arrow.businesstraceability.persistence.entity.Employee_mst middleApprovalEmployee;

    public arrow.businesstraceability.persistence.entity.Employee_mst getMiddleApprovalEmployee() {
        return this.middleApprovalEmployee;
    }

    public void setMiddleApprovalEmployee(
        final arrow.businesstraceability.persistence.entity.Employee_mst middleApprovalEmployee) {
        if (middleApprovalEmployee == null) {
            throw new IllegalArgumentException(
                "Param of Acc_com_credit.setMiddleApprovalEmployee(Employee_mst middleApprovalEmployee) must not be null");
        } else {
            this.setAc_middle_authorize(middleApprovalEmployee.getEmp_code());
        }
        this.middleApprovalEmployee = middleApprovalEmployee;
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "AC_FINAL_AUTHORIZE", referencedColumnName = "EMP_CODE", insertable = false,
        updatable = false)})
    protected arrow.businesstraceability.persistence.entity.Employee_mst finalApprovalEmployee;

    public arrow.businesstraceability.persistence.entity.Employee_mst getFinalApprovalEmployee() {
        return this.finalApprovalEmployee;
    }

    public void setFinalApprovalEmployee(
        final arrow.businesstraceability.persistence.entity.Employee_mst finalApprovalEmployee) {
        if (finalApprovalEmployee == null) {
            throw new IllegalArgumentException(
                "Param of Acc_com_credit.setFinalApprovalEmployee(Employee_mst finalApprovalEmployee) must not be null");
        } else {
            this.setAc_final_authorize(finalApprovalEmployee.getEmp_code());
        }
        this.finalApprovalEmployee = finalApprovalEmployee;
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "CODE_ACC_SURVEYER", referencedColumnName = "CODE", insertable = false,
        updatable = false)})
    protected arrow.businesstraceability.persistence.entity.Acc_com_bugyo16 accComBugyo16;

    public arrow.businesstraceability.persistence.entity.Acc_com_bugyo16 getAccComBugyo16() {
        return this.accComBugyo16;
    }

    public void setAccComBugyo16(final arrow.businesstraceability.persistence.entity.Acc_com_bugyo16 accComBugyo16) {
        if (accComBugyo16 == null) {
            throw new IllegalArgumentException(
                "Param of Acc_com_credit.setFinalApprovalEmployee(Employee_mst finalApprovalEmployee) must not be null");
        } else {
            this.setCode_acc_surveyer(accComBugyo16.getCode());
        }
        this.accComBugyo16 = accComBugyo16;
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "CODE_ACC_CREDITSCORE", referencedColumnName = "CODE", insertable = false,
        updatable = false)})
    protected arrow.businesstraceability.persistence.entity.Acc_com_bugyo15 accComBugyo15;

    public arrow.businesstraceability.persistence.entity.Acc_com_bugyo15 getAccComBugyo15() {
        return this.accComBugyo15;
    }

    public void setAccComBugyo15(final arrow.businesstraceability.persistence.entity.Acc_com_bugyo15 accComBugyo15) {
        if (accComBugyo15 == null) {
            throw new IllegalArgumentException(
                "Param of Acc_com_credit.setFinalApprovalEmployee(Employee_mst finalApprovalEmployee) must not be null");
        } else {
            this.setCode_acc_creditscore(accComBugyo15.getCode());
        }
        this.accComBugyo15 = accComBugyo15;
    }

    @Override
    public void setScore_credit(final java.lang.Integer score_credit) {
        if (score_credit != null) {
            List<Acc_com_bugyo15> listBugyo15 = Instance.get(CommonSelectorBean.class).getListAccComBugyo15();
            for (Acc_com_bugyo15 bugyo : listBugyo15) {
                if ((bugyo.getMin() == null) || (bugyo.getMax() == null)) {
                    continue;
                }

                if ((bugyo.getMin() <= score_credit) && (score_credit <= bugyo.getMax())) {
                    super.setCode_acc_creditscore(bugyo.getCode());
                    break;
                }
            }
        }
        super.setScore_credit(score_credit);
    }

    public boolean isApproved() {
        return (null != this.getStatus()) && (Constants.SAVED_CREDIT_STATUS == this.getStatus());
    }

    public boolean isDraft() {
        return (null != this.getStatus()) && (Constants.DRAFT_CREDIT_STATUS == this.getStatus());
    }

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}