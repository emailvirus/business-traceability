package arrow.businesstraceability.cache.entity;

public class PurposeCellData {
    private long newVisit;

    private long havePeriodicVisit;

    private long noPeriodicVisit;

    private long introductionEngineer;

    private long technicalDiscuss;

    private long quotation;

    private long complain;

    private long other;

    public long getNewVisit() {
        return this.newVisit;
    }

    public void setNewVisit(final long newVisit) {
        this.newVisit = newVisit;
    }

    public long getHavePeriodicVisit() {
        return this.havePeriodicVisit;
    }

    public void setHavePeriodicVisit(final long havePeriodicVisit) {
        this.havePeriodicVisit = havePeriodicVisit;
    }

    public long getNoPeriodicVisit() {
        return this.noPeriodicVisit;
    }

    public void setNoPeriodicVisit(final long noPeriodicVisit) {
        this.noPeriodicVisit = noPeriodicVisit;
    }

    public long getIntroductionEngineer() {
        return this.introductionEngineer;
    }

    public void setIntroductionEngineer(final long introductionEngineer) {
        this.introductionEngineer = introductionEngineer;
    }

    public long getTechnicalDiscuss() {
        return this.technicalDiscuss;
    }

    public void setTechnicalDiscuss(final long technicalDiscuss) {
        this.technicalDiscuss = technicalDiscuss;
    }

    public long getQuotation() {
        return this.quotation;
    }

    public void setQuotation(final long quotation) {
        this.quotation = quotation;
    }

    public long getComplain() {
        return this.complain;
    }

    public void setComplain(final long complain) {
        this.complain = complain;
    }

    public long getOther() {
        return this.other;
    }

    public void setOther(final long other) {
        this.other = other;
    }
}
