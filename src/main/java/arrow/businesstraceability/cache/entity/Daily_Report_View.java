package arrow.businesstraceability.cache.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Daily_Report_View {

    private int id;

    private String emp_name;

    private String com_company_name;

    private String dat_name;

    private String adp_name;

    private String big_name;

    public Daily_Report_View() {}

    public Daily_Report_View(final int id) {
        this.id = id;
    }

    /**
     * Instantiates a new daily_ report_ view.
     *
     * @param id the id
     * @param emp_name the emp_name
     * @param com_company_name the com_company_name
     * @param dat_name the dat_name
     * @param adp_name the adp_name
     */
    public Daily_Report_View(final int id, final String emp_name, final String com_company_name, final String dat_name,
        final String adp_name) {
        this.setId(id);
        this.emp_name = emp_name;
        this.com_company_name = com_company_name;
        this.dat_name = dat_name;
        this.adp_name = adp_name;
    }

    public String getEmp_name() {
        return this.emp_name;
    }

    public void setEmp_name(final String emp_name) {
        this.emp_name = emp_name;
    }

    public String getCom_company_name() {
        return this.com_company_name;
    }

    public void setCom_company_name(final String com_company_name) {
        this.com_company_name = com_company_name;
    }

    public String getDat_name() {
        return this.dat_name;
    }

    public void setDat_name(final String dat_name) {
        this.dat_name = dat_name;
    }

    public String getAdp_name() {
        return this.adp_name;
    }

    public void setAdp_name(final String adp_name) {
        this.adp_name = adp_name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getBig_name() {
        return this.big_name;
    }

    public void setBig_name(final String big_name) {
        this.big_name = big_name;
    }
}
