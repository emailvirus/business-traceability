package arrow.businesstraceability.control.bean;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import arrow.businesstraceability.control.service.AccComBugyoService;
import arrow.businesstraceability.persistence.entity.Acc_com_bugyo12;
import arrow.businesstraceability.persistence.entity.Acc_com_bugyo14;
import arrow.businesstraceability.persistence.entity.Acc_com_bugyo16;
import arrow.businesstraceability.persistence.entity.Acc_com_bugyo90;
import arrow.businesstraceability.persistence.entity.Acc_com_bugyo91;
import arrow.framework.bean.AbstractBean;
import arrow.framework.util.collections.CollectionUtils;

/**
 * The type Acc com bugyo bean.
 */
@Named
@ViewScoped
public class AccComBugyoBean extends AbstractBean {

    @Inject
    private AccComBugyoService accComBugyoService;

    private List<Acc_com_bugyo12> listAccComBugyo12;

    private List<Acc_com_bugyo14> listAccComBugyo14;

    private List<Acc_com_bugyo16> listAccComBugyo16;

    private List<Acc_com_bugyo90> listAccComBugyo90;

    private List<Acc_com_bugyo91> listAccComBugyo91;

    /**
     * Gets list acc com bugyo 91.
     *
     * @return the list acc com bugyo 91
     */
    public List<Acc_com_bugyo91> getListAccComBugyo91() {
        if (CollectionUtils.isEmpty(this.listAccComBugyo91)) {
            this.listAccComBugyo91 = this.accComBugyoService.getAllAccComBugyo91();
        }
        return this.listAccComBugyo91;
    }

    /**
     * Sets list acc com bugyo 91.
     *
     * @param listAccComBugyo91 the list acc com bugyo 91
     */
    public void setListAccComBugyo91(final List<Acc_com_bugyo91> listAccComBugyo91) {
        this.listAccComBugyo91 = listAccComBugyo91;
    }

    /**
     * Gets list acc com bugyo 16.
     *
     * @return the list acc com bugyo 16
     */
    public List<Acc_com_bugyo16> getListAccComBugyo16() {
        if (CollectionUtils.isEmpty(this.listAccComBugyo16)) {
            this.listAccComBugyo16 = this.accComBugyoService.getAllAccComBugyo16();
        }
        return this.listAccComBugyo16;
    }

    /**
     * Sets list acc com bugyo 16.
     *
     * @param listAccComBugyo16 the list acc com bugyo 16
     */
    public void setListAccComBugyo16(final List<Acc_com_bugyo16> listAccComBugyo16) {
        this.listAccComBugyo16 = listAccComBugyo16;
    }

    /**
     * Gets list acc com bugyo 90.
     *
     * @return the list acc com bugyo 90
     */
    public List<Acc_com_bugyo90> getListAccComBugyo90() {
        if (CollectionUtils.isEmpty(this.listAccComBugyo90)) {
            this.listAccComBugyo90 = this.accComBugyoService.getAllAccComBugyo90();
        }
        return this.listAccComBugyo90;
    }

    /**
     * Sets list acc com bugyo 90.
     *
     * @param listAccComBugyo90 the list acc com bugyo 90
     */
    public void setListAccComBugyo90(final List<Acc_com_bugyo90> listAccComBugyo90) {
        this.listAccComBugyo90 = listAccComBugyo90;
    }

    /**
     * Gets list acc com bugyo 14.
     *
     * @return the list acc com bugyo 14
     */
    public List<Acc_com_bugyo14> getListAccComBugyo14() {
        if (CollectionUtils.isEmpty(this.listAccComBugyo14)) {
            this.listAccComBugyo14 = this.accComBugyoService.getAllAccComBugyo14();
        }
        return this.listAccComBugyo14;
    }

    /**
     * Sets list acc com bugyo 14.
     *
     * @param listAccComBugyo14 the list acc com bugyo 14
     */
    public void setListAccComBugyo14(final List<Acc_com_bugyo14> listAccComBugyo14) {
        this.listAccComBugyo14 = listAccComBugyo14;
    }

    /**
     * Gets list acc com bugyo 12.
     *
     * @return the list acc com bugyo 12
     */
    public List<Acc_com_bugyo12> getListAccComBugyo12() {
        if (CollectionUtils.isEmpty(this.listAccComBugyo12)) {
            this.listAccComBugyo12 = this.accComBugyoService.getAllAccComBugyo12();
        }
        return this.listAccComBugyo12;
    }

    /**
     * Sets list acc com bugyo 12.
     *
     * @param listAccComBugyo12 the list acc com bugyo 12
     */
    public void setListAccComBugyo12(final List<Acc_com_bugyo12> listAccComBugyo12) {
        this.listAccComBugyo12 = listAccComBugyo12;
    }
}
