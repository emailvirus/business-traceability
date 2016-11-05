//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.mapped.San_idmap_person_MAPPED;

import arrow.businesstraceability.persistence.entity.San_idmap_person;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class San_idmap_person_DTO extends San_idmap_person {
    private San_idmap_person_MAPPED.Pk pk;

    public void setPk(final San_idmap_person_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public San_idmap_person_MAPPED.Pk getPk() {
        return this.pk;
    }

    private int id_person;

    @Override
    public int getId_person() {
        return this.id_person;
    }

    public void setId_person(final int id_person) {
        this.id_person = id_person;
    }
  

    private boolean isPersisted;

    @Override
    public boolean isPersisted() {
        return this.isPersisted;
    }

    public void setPersisted(final boolean value) {
        this.isPersisted = value;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}