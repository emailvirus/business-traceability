package arrow.businesstraceability.event.observe;

import arrow.businesstraceability.persistence.entity.Addresspoint_mst;

public class AddressSelectEvent {
    private String eventName;

    private Addresspoint_mst branchSelected;

    public AddressSelectEvent(final String eventName, final Addresspoint_mst branchSelected) {
        this.eventName = eventName;
        this.branchSelected = branchSelected;
    }

    public String getEventName() {
        return this.eventName;
    }

    public void setEventName(final String eventName) {
        this.eventName = eventName;
    }

    public Addresspoint_mst getBranchSelected() {
        return this.branchSelected;
    }

    public void setBranchSelected(final Addresspoint_mst branchSelected) {
        this.branchSelected = branchSelected;
    }
}
