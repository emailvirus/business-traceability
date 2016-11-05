//@formatter:off
package arrow.businesstraceability.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public abstract class Abstract_entity_MAPPED extends arrow.framework.persistence.BaseEntity {

    //Normal columns

    @Column
    private java.util.Date last_updated_at;

    public java.util.Date getLast_updated_at() {
        return this.last_updated_at;
    }

    public final java.util.Date getLast_updated_at_DIRECT() {
        return this.last_updated_at;
    }

    public void setLast_updated_at(final java.util.Date last_updated_at) {
        this.last_updated_at = last_updated_at;
    }

    public final void setLast_updated_at_DIRECT(final java.util.Date last_updated_at) {
        this.last_updated_at = last_updated_at;
    }

    @Version
    @Column
    private java.lang.Integer object_version;

    public java.lang.Integer getObject_version() {
        return this.object_version;
    }

    public final java.lang.Integer getObject_version_DIRECT() {
        return this.object_version;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    public void setObject_version(final java.lang.Integer object_version) {
        this.object_version = object_version;
    }

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}