package arrow.framework.faces.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.omnifaces.util.Components;

import arrow.businesstraceability.cache.entity.Company_mst_suggest;
import arrow.businesstraceability.control.service.CompanyManagementService;
import arrow.businesstraceability.persistence.entity.Company_mst;
import arrow.businesstraceability.util.TransformUtils;
import arrow.framework.faces.util.ConverterUtils;
import arrow.framework.util.StringUtils;
import arrow.framework.util.cdi.Instance;

public class TemporaryEntityConverter implements Converter {

    /**
     * This method will call when select record in auto complete.
     */
    @Override
    public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
        if ((value == null) || (value.trim().length() == 0)) {
            final Boolean required = Components.getAttribute(component, "required");

            if ((required != null) && required) {
                throw ConverterUtils.throwConverterException(component, "converter.inputRequired");
            }
        }

        Company_mst company = Instance.get(CompanyManagementService.class).findCompanyByCompanyCode(value);
        return TransformUtils.transform(company);
    }

    /**
     * This method will call when show data of object on screen.
     */
    @Override
    public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
        if (value == null) {
            return StringUtils.EMPTY_STRING;
        }
        Company_mst_suggest companySuggest = (Company_mst_suggest) value;
        return companySuggest.getCom_company_code();
    }
}
