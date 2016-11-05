package arrow.businesstraceability.event.observe;

import arrow.businesstraceability.persistence.entity.Company_mst;

public class CompanySearchEvent {
    /** The event name. */
    private final String eventName;

    /** The selected company. */
    private final Company_mst selectedCompany;

    /**
     * Instantiates a new company search event.
     *
     * @param eventName the event name
     * @param selectedCompany the selected company
     */
    public CompanySearchEvent(final String eventName, final Company_mst selectedCompany) {
        this.eventName = eventName;
        this.selectedCompany = selectedCompany;
    }

    /**
     * Gets the event name.
     *
     * @return the event name
     */
    public String getEventName() {
        return this.eventName;
    }

    /**
     * Gets the selected company.
     *
     * @return the selected company
     */
    public Company_mst getSelectedCompany() {
        return this.selectedCompany;
    }
}
