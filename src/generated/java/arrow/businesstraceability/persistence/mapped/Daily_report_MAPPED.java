//@formatter:off
package arrow.businesstraceability.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import arrow.businesstraceability.persistence.entity.Abstract_entity;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class Daily_report_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.Daily_report> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.Daily_report.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param dai_employee_code Data type: int
         * @param dai_point_code Data type: java.lang.String
         * @param dai_work_date Data type: java.util.Date
         * @param dai_work_stime Data type: java.util.Date
         * @param dai_work_etime Data type: java.util.Date
         *
         */
        public Pk(final int dai_employee_code, final java.lang.String dai_point_code, final java.util.Date dai_work_date, final java.util.Date dai_work_stime, final java.util.Date dai_work_etime) {
            this.dai_employee_code = dai_employee_code;
            this.dai_point_code = dai_point_code;
            this.dai_work_date = dai_work_date;
            this.dai_work_stime = dai_work_stime;
            this.dai_work_etime = dai_work_etime;
        }

        @Column(name = "DAI_EMPLOYEE_CODE")
        protected int dai_employee_code;

        public int getDai_employee_code() {
            return this.dai_employee_code;
        }

        @Column(name = "DAI_POINT_CODE")
        protected java.lang.String dai_point_code;

        public java.lang.String getDai_point_code() {
            return this.dai_point_code;
        }

        @Column(name = "DAI_WORK_DATE")
        protected java.util.Date dai_work_date;

        public java.util.Date getDai_work_date() {
            return this.dai_work_date;
        }

        @Column(name = "DAI_WORK_STIME")
        protected java.util.Date dai_work_stime;

        public java.util.Date getDai_work_stime() {
            return this.dai_work_stime;
        }

        @Column(name = "DAI_WORK_ETIME")
        protected java.util.Date dai_work_etime;

        public java.util.Date getDai_work_etime() {
            return this.dai_work_etime;
        }
    }

    //default constructor
    public Daily_report_MAPPED() {
    }

    protected Daily_report_MAPPED(final arrow.businesstraceability.persistence.entity.Employee_mst employee_mst, final arrow.businesstraceability.persistence.entity.Addresspoint_mst addresspoint_mst, final java.util.Date dai_work_date, final java.util.Date dai_work_stime, final java.util.Date dai_work_etime) {
        this.checkFKConsistency(employee_mst, addresspoint_mst, dai_work_date, dai_work_stime, dai_work_etime);
        this.pk = new Pk(employee_mst.getEmp_code(), addresspoint_mst.getAdp_code(), dai_work_date, dai_work_stime, dai_work_etime);
        this.employee_mst = employee_mst;
        this.addresspoint_mst = addresspoint_mst;
        this.dai_work_date = dai_work_date;
        this.dai_work_stime = dai_work_stime;
        this.dai_work_etime = dai_work_etime;
        this.dai_employee_code = employee_mst.getEmp_code();
        this.dai_point_code = addresspoint_mst.getAdp_code();
    }

    private void checkFKConsistency(arrow.businesstraceability.persistence.entity.Employee_mst employee_mst, arrow.businesstraceability.persistence.entity.Addresspoint_mst addresspoint_mst, java.util.Date dai_work_date, java.util.Date dai_work_stime, java.util.Date dai_work_etime) {
        if (employee_mst == null) {
            throw new IllegalArgumentException("Parameter employee_mst must not be null");
        }
        if (addresspoint_mst == null) {
            throw new IllegalArgumentException("Parameter addresspoint_mst must not be null");
        }
        if (dai_work_date == null) {
            throw new IllegalArgumentException("Parameter dai_work_date must not be null");
        }
        if (dai_work_stime == null) {
            throw new IllegalArgumentException("Parameter dai_work_stime must not be null");
        }
        if (dai_work_etime == null) {
            throw new IllegalArgumentException("Parameter dai_work_etime must not be null");
        }
    }

    //Primary Key
    //Should be initialized only once by the constructor, thus there's no setters
    @EmbeddedId
    protected Pk pk;
    @Override
    public Pk getPk() {
        return this.pk;
    }

    //PK columns
    //Should have insertable=false, updatable=false, and no setters

    @Column(insertable = false, updatable = false)
    protected int dai_employee_code;

    public int getDai_employee_code() {
        return this.dai_employee_code;
    }

    @Column(insertable = false, updatable = false)
    protected java.lang.String dai_point_code;

    public java.lang.String getDai_point_code() {
        return this.dai_point_code;
    }

    @Column(insertable = false, updatable = false)
    protected java.util.Date dai_work_date;

    public java.util.Date getDai_work_date() {
        return this.dai_work_date;
    }

    @Column(insertable = false, updatable = false)
    protected java.util.Date dai_work_stime;

    public java.util.Date getDai_work_stime() {
        return this.dai_work_stime;
    }

    @Column(insertable = false, updatable = false)
    protected java.util.Date dai_work_etime;

    public java.util.Date getDai_work_etime() {
        return this.dai_work_etime;
    }

    //Normal columns

    @Column
    private java.lang.Boolean dai_anken_flg;

    public java.lang.Boolean getDai_anken_flg() {
        return this.dai_anken_flg;
    }

    public final java.lang.Boolean getDai_anken_flg_DIRECT() {
        return this.dai_anken_flg;
    }

    public void setDai_anken_flg(final java.lang.Boolean dai_anken_flg) {
        this.dai_anken_flg = dai_anken_flg;
    }

    public final void setDai_anken_flg_DIRECT(final java.lang.Boolean dai_anken_flg) {
        this.dai_anken_flg = dai_anken_flg;
    }

    @Column
    private java.lang.String dai_business_details;

    public java.lang.String getDai_business_details() {
        return this.dai_business_details;
    }

    public final java.lang.String getDai_business_details_DIRECT() {
        return this.dai_business_details;
    }

    public void setDai_business_details(final java.lang.String dai_business_details) {
        this.dai_business_details = dai_business_details;
    }

    public final void setDai_business_details_DIRECT(final java.lang.String dai_business_details) {
        this.dai_business_details = dai_business_details;
    }

    @Column
    private java.lang.Short dai_bus_code;

    public java.lang.Short getDai_bus_code() {
        return this.dai_bus_code;
    }

    public final java.lang.Short getDai_bus_code_DIRECT() {
        return this.dai_bus_code;
    }

    public void setDai_bus_code(final java.lang.Short dai_bus_code) {
        this.dai_bus_code = dai_bus_code;
    }

    public final void setDai_bus_code_DIRECT(final java.lang.Short dai_bus_code) {
        this.dai_bus_code = dai_bus_code;
    }

    @Column
    private java.lang.String dai_company_code;

    public java.lang.String getDai_company_code() {
        return this.dai_company_code;
    }

    public final java.lang.String getDai_company_code_DIRECT() {
        return this.dai_company_code;
    }

    public void setDai_company_code(final java.lang.String dai_company_code) {
        this.dai_company_code = dai_company_code;
    }

    public final void setDai_company_code_DIRECT(final java.lang.String dai_company_code) {
        this.dai_company_code = dai_company_code;
    }

    @Column
    private java.lang.String dai_compemp_name;

    public java.lang.String getDai_compemp_name() {
        return this.dai_compemp_name;
    }

    public final java.lang.String getDai_compemp_name_DIRECT() {
        return this.dai_compemp_name;
    }

    public void setDai_compemp_name(final java.lang.String dai_compemp_name) {
        this.dai_compemp_name = dai_compemp_name;
    }

    public final void setDai_compemp_name_DIRECT(final java.lang.String dai_compemp_name) {
        this.dai_compemp_name = dai_compemp_name;
    }

    @Column
    private java.lang.String dai_comppoint_code;

    public java.lang.String getDai_comppoint_code() {
        return this.dai_comppoint_code;
    }

    public final java.lang.String getDai_comppoint_code_DIRECT() {
        return this.dai_comppoint_code;
    }

    public void setDai_comppoint_code(final java.lang.String dai_comppoint_code) {
        this.dai_comppoint_code = dai_comppoint_code;
    }

    public final void setDai_comppoint_code_DIRECT(final java.lang.String dai_comppoint_code) {
        this.dai_comppoint_code = dai_comppoint_code;
    }

    @Column
    private java.lang.String dai_employee_post;

    public java.lang.String getDai_employee_post() {
        return this.dai_employee_post;
    }

    public final java.lang.String getDai_employee_post_DIRECT() {
        return this.dai_employee_post;
    }

    public void setDai_employee_post(final java.lang.String dai_employee_post) {
        this.dai_employee_post = dai_employee_post;
    }

    public final void setDai_employee_post_DIRECT(final java.lang.String dai_employee_post) {
        this.dai_employee_post = dai_employee_post;
    }

    @Column
    private java.lang.Boolean dai_matter_flg;

    public java.lang.Boolean getDai_matter_flg() {
        return this.dai_matter_flg;
    }

    public final java.lang.Boolean getDai_matter_flg_DIRECT() {
        return this.dai_matter_flg;
    }

    public void setDai_matter_flg(final java.lang.Boolean dai_matter_flg) {
        this.dai_matter_flg = dai_matter_flg;
    }

    public final void setDai_matter_flg_DIRECT(final java.lang.Boolean dai_matter_flg) {
        this.dai_matter_flg = dai_matter_flg;
    }

    @Column
    private java.util.Date dai_required_time;

    public java.util.Date getDai_required_time() {
        return this.dai_required_time;
    }

    public final java.util.Date getDai_required_time_DIRECT() {
        return this.dai_required_time;
    }

    public void setDai_required_time(final java.util.Date dai_required_time) {
        this.dai_required_time = dai_required_time;
    }

    public final void setDai_required_time_DIRECT(final java.util.Date dai_required_time) {
        this.dai_required_time = dai_required_time;
    }

    @Column
    private java.lang.Boolean dai_rimaindar_flg;

    public java.lang.Boolean getDai_rimaindar_flg() {
        return this.dai_rimaindar_flg;
    }

    public final java.lang.Boolean getDai_rimaindar_flg_DIRECT() {
        return this.dai_rimaindar_flg;
    }

    public void setDai_rimaindar_flg(final java.lang.Boolean dai_rimaindar_flg) {
        this.dai_rimaindar_flg = dai_rimaindar_flg;
    }

    public final void setDai_rimaindar_flg_DIRECT(final java.lang.Boolean dai_rimaindar_flg) {
        this.dai_rimaindar_flg = dai_rimaindar_flg;
    }

    @Column
    private java.util.Date dai_summary_etime;

    public java.util.Date getDai_summary_etime() {
        return this.dai_summary_etime;
    }

    public final java.util.Date getDai_summary_etime_DIRECT() {
        return this.dai_summary_etime;
    }

    public void setDai_summary_etime(final java.util.Date dai_summary_etime) {
        this.dai_summary_etime = dai_summary_etime;
    }

    public final void setDai_summary_etime_DIRECT(final java.util.Date dai_summary_etime) {
        this.dai_summary_etime = dai_summary_etime;
    }

    @Column
    private java.util.Date dai_summary_stime;

    public java.util.Date getDai_summary_stime() {
        return this.dai_summary_stime;
    }

    public final java.util.Date getDai_summary_stime_DIRECT() {
        return this.dai_summary_stime;
    }

    public void setDai_summary_stime(final java.util.Date dai_summary_stime) {
        this.dai_summary_stime = dai_summary_stime;
    }

    public final void setDai_summary_stime_DIRECT(final java.util.Date dai_summary_stime) {
        this.dai_summary_stime = dai_summary_stime;
    }

    @Column
    private java.util.Date dai_update_date;

    public java.util.Date getDai_update_date() {
        return this.dai_update_date;
    }

    public final java.util.Date getDai_update_date_DIRECT() {
        return this.dai_update_date;
    }

    public void setDai_update_date(final java.util.Date dai_update_date) {
        this.dai_update_date = dai_update_date;
    }

    public final void setDai_update_date_DIRECT(final java.util.Date dai_update_date) {
        this.dai_update_date = dai_update_date;
    }

    @Column
    private java.lang.Short dai_work_tancode;

    public java.lang.Short getDai_work_tancode() {
        return this.dai_work_tancode;
    }

    public final java.lang.Short getDai_work_tancode_DIRECT() {
        return this.dai_work_tancode;
    }

    public void setDai_work_tancode(final java.lang.Short dai_work_tancode) {
        this.dai_work_tancode = dai_work_tancode;
    }

    public final void setDai_work_tancode_DIRECT(final java.lang.Short dai_work_tancode) {
        this.dai_work_tancode = dai_work_tancode;
    }

    @Column
    private java.lang.Boolean dai_work_type;

    public java.lang.Boolean getDai_work_type() {
        return this.dai_work_type;
    }

    public final java.lang.Boolean getDai_work_type_DIRECT() {
        return this.dai_work_type;
    }

    public void setDai_work_type(final java.lang.Boolean dai_work_type) {
        this.dai_work_type = dai_work_type;
    }

    public final void setDai_work_type_DIRECT(final java.lang.Boolean dai_work_type) {
        this.dai_work_type = dai_work_type;
    }

    //Foreign keys

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "DAI_EMPLOYEE_CODE", referencedColumnName = "EMP_CODE", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.Employee_mst employee_mst;

    public arrow.businesstraceability.persistence.entity.Employee_mst getEmployee_mst() {
        return this.employee_mst;
    }

    /**
     * Set Employee_mst for Daily_report_MAPPED.
     *
     * @param employee_mst Employee_mst.
     *
     **/
    public void setEmployee_mst(final arrow.businesstraceability.persistence.entity.Employee_mst employee_mst) {
        if (employee_mst == null) {
            throw new IllegalArgumentException(
                    "Param of Daily_report_MAPPED.setEmployee_mst(Employee_mst employee_mst) must not be null");
        }
        else {
            this.dai_employee_code = employee_mst.getEmp_code();
        }
        this.employee_mst = employee_mst;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "DAI_POINT_CODE", referencedColumnName = "ADP_CODE", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.Addresspoint_mst addresspoint_mst;

    public arrow.businesstraceability.persistence.entity.Addresspoint_mst getAddresspoint_mst() {
        return this.addresspoint_mst;
    }

    /**
     * Set Addresspoint_mst for Daily_report_MAPPED.
     *
     * @param addresspoint_mst Addresspoint_mst.
     *
     **/
    public void setAddresspoint_mst(final arrow.businesstraceability.persistence.entity.Addresspoint_mst addresspoint_mst) {
        if (addresspoint_mst == null) {
            throw new IllegalArgumentException(
                    "Param of Daily_report_MAPPED.setAddresspoint_mst(Addresspoint_mst addresspoint_mst) must not be null");
        }
        else {
            this.dai_point_code = addresspoint_mst.getAdp_code();
        }
        this.addresspoint_mst = addresspoint_mst;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "DAI_COMPANY_CODE", referencedColumnName = "COM_COMPANY_CODE", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.Company_mst company_mst;

    public arrow.businesstraceability.persistence.entity.Company_mst getCompany_mst() {
        return this.company_mst;
    }

    /**
     * Set Company_mst for Daily_report_MAPPED.
     *
     * @param company_mst Company_mst.
     *
     **/
    public void setCompany_mst(final arrow.businesstraceability.persistence.entity.Company_mst company_mst) {
        if (company_mst == null) {
            this.dai_company_code = null;
        }
        else {
            this.dai_company_code = company_mst.getCom_company_code();
        }
        this.company_mst = company_mst;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "DAI_WORK_TANCODE", referencedColumnName = "DAT_CODE", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.Daily_activity_type daily_activity_type;

    public arrow.businesstraceability.persistence.entity.Daily_activity_type getDaily_activity_type() {
        return this.daily_activity_type;
    }

    /**
     * Set Daily_activity_type for Daily_report_MAPPED.
     *
     * @param daily_activity_type Daily_activity_type.
     *
     **/
    public void setDaily_activity_type(final arrow.businesstraceability.persistence.entity.Daily_activity_type daily_activity_type) {
        if (daily_activity_type == null) {
            this.dai_work_tancode = null;
        }
        else {
            this.dai_work_tancode = daily_activity_type.getDat_code();
        }
        this.daily_activity_type = daily_activity_type;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "DAI_BUS_CODE", referencedColumnName = "BIG_CODE", insertable = false,
    updatable = false)})
    protected arrow.businesstraceability.persistence.entity.Industry_big_mst industry_big_mst;

    public arrow.businesstraceability.persistence.entity.Industry_big_mst getIndustry_big_mst() {
        return this.industry_big_mst;
    }

    public void setIndustry_big_mst(
            final arrow.businesstraceability.persistence.entity.Industry_big_mst industry_big_mst) {
        if (industry_big_mst == null) {
            this.dai_bus_code = null;
        } else {

            this.dai_bus_code = industry_big_mst.getBig_code();
        }
        this.industry_big_mst = industry_big_mst;
    }

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}