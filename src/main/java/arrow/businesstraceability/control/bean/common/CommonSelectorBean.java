package arrow.businesstraceability.control.bean.common;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;

import arrow.businesstraceability.persistence.entity.Acc_com_bugyo15;
import arrow.businesstraceability.persistence.repository.Acc_com_bugyo15Repository;
import arrow.framework.bean.AbstractBean;

// TODO: Auto-generated Javadoc
/**
 * The Class CommonSelectorBean.
 */
@Named
@SessionScoped
public class CommonSelectorBean extends AbstractBean {

    /** The list acc com bugyo15. */
    private List<Acc_com_bugyo15> listAccComBugyo15;

    /** The bugyo15 repo. */
    @Inject
    private Acc_com_bugyo15Repository bugyo15Repo;

    /**
     * Gets the list acc com bugyo15.
     *
     * @return the list acc com bugyo15
     */
    public List<Acc_com_bugyo15> getListAccComBugyo15() {
        if (CollectionUtils.isEmpty(this.listAccComBugyo15)) {
            this.listAccComBugyo15 = this.bugyo15Repo.findAll();
        }
        return this.listAccComBugyo15;
    }

    /**
     * Sets the list acc com bugyo15.
     *
     * @param listAccComBugyo15 the new list acc com bugyo15
     */
    public void setListAccComBugyo15(final List<Acc_com_bugyo15> listAccComBugyo15) {
        this.listAccComBugyo15 = listAccComBugyo15;
    }
}
