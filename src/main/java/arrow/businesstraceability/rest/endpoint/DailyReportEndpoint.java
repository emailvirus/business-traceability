package arrow.businesstraceability.rest.endpoint;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import arrow.businesstraceability.cache.entity.Company_mst_suggest;
import arrow.businesstraceability.cache.entity.Daily_Report_View;
import arrow.businesstraceability.constant.ElasticSearchConstants;
import arrow.businesstraceability.control.service.ElasticSearchService;
import arrow.businesstraceability.persistence.entity.Customer_info_view;
import arrow.framework.util.collections.CollectionUtils;

@Path("/dailyreport")
@RequestScoped
public class DailyReportEndpoint extends AbstractEndpoint {

    @Inject
    private ElasticSearchService elasticSearchService;

    /**
     * Gets the person in charge suggestion.
     *
     * @param employeeName the employee name
     * @param comPointCode the company position code
     * @param companyCode the company code
     * @return the person in charge suggestion
     */
    @GET
    @Path("/suggest/employee/{employeeName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonInChargeSuggestion(@PathParam("employeeName") final String employeeName,
        @HeaderParam("comPointCode") final String comPointCode, @HeaderParam("companyCode") final String companyCode) {
        final List<Customer_info_view> listCustomer =
            this.elasticSearchService.suggestPersonInChargeOrDepartment(employeeName, comPointCode, companyCode,
                ElasticSearchConstants.CustomerInfoView.DAI_COMEMP_NAME);
        if (CollectionUtils.isNotEmpty(listCustomer)) {
            return Response.status(Status.OK).entity(listCustomer).build();
        }
        return Response.status(Status.NO_CONTENT).build();
    }

    /**
     * Gets the department suggestion.
     *
     * @param departmentName the department name
     * @param comPointCode the company position code
     * @param companyCode the company code
     * @return the department suggestion
     */
    @GET
    @Path("/suggest/department/{departmentName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDepartmentSuggestion(@PathParam("departmentName") final String departmentName,
        @HeaderParam("comPointCode") final String comPointCode, @HeaderParam("companyCode") final String companyCode) {
        final List<Customer_info_view> listCustomer =
            this.elasticSearchService.suggestPersonInChargeOrDepartment(departmentName, comPointCode, companyCode,
                ElasticSearchConstants.CustomerInfoView.DAI_COMEMP_POST);
        if (CollectionUtils.isNotEmpty(listCustomer)) {
            return Response.ok(listCustomer).build();
        }
        return Response.noContent().build();

    }

    /**
     * Gets the daily report pivot.
     *
     * @return the daily report pivot
     */
    @GET
    @Path("/pivot/test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDailyReportPivot() {
        List<Daily_Report_View> listReports = this.elasticSearchService.getAllDailyReports();
        if (CollectionUtils.isNotEmpty(listReports)) {
            return Response.status(Status.OK).entity(listReports).build();
        }
        return Response.noContent().build();
    }

    /**
     * Gets the customer info suggestion.
     *
     * @param query the query
     * @return the customer info suggestion
     */
    @GET
    @Path("/suggest/company/{query}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerInfoSuggestion(@PathParam("query") final String query) {
        final List<Company_mst_suggest> listCompanies = this.elasticSearchService.suggestCompany(query).getData();
        if (CollectionUtils.isNotEmpty(listCompanies)) {
            return Response.status(Status.OK).entity(listCompanies).build();
        }
        return Response.noContent().build();
    }
}
