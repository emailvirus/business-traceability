package arrow.businesstraceability.cache.entity;


public class FindDataNameCardEntity {

    private String companyName;

    private int countUrlOfCompany;

    private int sumOfCard;

    private int sumOfOwner;

    private int sumOfClient;

    private String latestChanged;

    private String oldestChanged;

    private int regularContact;

    private int freqOfAccess;

    private boolean selected;

    private int idCompany;

    private String idSanCompany;

    private Integer idComEntity;

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(final String companyName) {
        this.companyName = companyName;
    }


    public int getCountUrlOfCompany() {
        return this.countUrlOfCompany;
    }

    public void setCountUrlOfCompany(final int countUrlOfCompany) {
        this.countUrlOfCompany = countUrlOfCompany;
    }

    public int getSumOfCard() {
        return this.sumOfCard;
    }

    public void setSumOfCard(final int sumOfCard) {
        this.sumOfCard = sumOfCard;
    }

    public int getSumOfOwner() {
        return this.sumOfOwner;
    }

    public void setSumOfOwner(final int sumOfOwner) {
        this.sumOfOwner = sumOfOwner;
    }

    public int getSumOfClient() {
        return this.sumOfClient;
    }

    public void setSumOfClient(final int sumOfClient) {
        this.sumOfClient = sumOfClient;
    }

    public String getLatestChanged() {
        return this.latestChanged;
    }

    public void setLatestChanged(final String latestChanged) {
        this.latestChanged = latestChanged;
    }

    public String getOldestChanged() {
        return this.oldestChanged;
    }

    public void setOldestChanged(final String oldestChanged) {
        this.oldestChanged = oldestChanged;
    }

    public int getRegularContact() {
        return this.regularContact;
    }

    public void setRegularContact(final int regularContact) {
        this.regularContact = regularContact;
    }

    public int getFreqOfAccess() {
        return this.freqOfAccess;
    }

    public void setFreqOfAccess(final int freqOfAccess) {
        this.freqOfAccess = freqOfAccess;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(final boolean selected) {
        this.selected = selected;
    }

    public int getIdCompany() {
        return this.idCompany;
    }

    public void setIdCompany(final int idCompany) {
        this.idCompany = idCompany;
    }

    public String getIdSanCompany() {
        return this.idSanCompany;
    }

    public void setIdSanCompany(final String idSanCompany) {
        this.idSanCompany = idSanCompany;
    }

    public Integer getIdComEntity() {
        return idComEntity;
    }

    public void setIdComEntity(Integer idComEntity) {
        this.idComEntity = idComEntity;
    }

}
