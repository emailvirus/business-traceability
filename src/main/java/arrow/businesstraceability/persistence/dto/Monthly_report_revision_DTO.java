//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.mapped.Monthly_report_revision_MAPPED;

import arrow.businesstraceability.persistence.entity.Monthly_report_revision;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class Monthly_report_revision_DTO extends Monthly_report_revision {
    private Monthly_report_revision_MAPPED.Pk pk;

    public void setPk(final Monthly_report_revision_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public Monthly_report_revision_MAPPED.Pk getPk() {
        return this.pk;
    }

    private int bajon_bangou;

    @Override
    public int getBajon_bangou() {
        return this.bajon_bangou;
    }

    public void setBajon_bangou(final int bajon_bangou) {
        this.bajon_bangou = bajon_bangou;
    }
  
    private int shain_kodo;

    @Override
    public int getShain_kodo() {
        return this.shain_kodo;
    }

    public void setShain_kodo(final int shain_kodo) {
        this.shain_kodo = shain_kodo;
    }
  
    private int repoto_no_getsudo;

    @Override
    public int getRepoto_no_getsudo() {
        return this.repoto_no_getsudo;
    }

    public void setRepoto_no_getsudo(final int repoto_no_getsudo) {
        this.repoto_no_getsudo = repoto_no_getsudo;
    }
  
    private int repoto_no_nendo;

    @Override
    public int getRepoto_no_nendo() {
        return this.repoto_no_nendo;
    }

    public void setRepoto_no_nendo(final int repoto_no_nendo) {
        this.repoto_no_nendo = repoto_no_nendo;
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