//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import arrow.businesstraceability.persistence.mapped.Abstract_entity_MAPPED;
import arrow.framework.util.DateUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public abstract class Abstract_entity extends Abstract_entity_MAPPED {

    public Abstract_entity() {
    }

    @Transient
    private boolean isPersisted;

    public boolean isPersisted() {
        return this.isPersisted;
    }

    // used by BeanCopier
    public boolean getPersisted() {
        return this.isPersisted;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @PostLoad
    public void postLoad() {
        this.isPersisted = true;
    }

    @Override
    @PostPersist
    public void postPersist() {
        this.isPersisted = true;
        super.postPersist();
    }

    @PrePersist
    public void prePersist() {

        // Always update LAST_UPDATED_AT
        this.setLast_updated_at_DIRECT(DateUtils.getCurrentDatetime());
    }

    @PreUpdate
    public void preUpdate() {

        // Always update LAST_UPDATED_AT
        this.setLast_updated_at_DIRECT(DateUtils.getCurrentDatetime());
    }

    @Transient
    @JsonIgnore
    protected boolean selected;

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(final boolean selected) {
        this.selected = selected;
    }

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}