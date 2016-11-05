package arrow.businesstraceability.cache.entity;

import java.util.Date;

public class SansanData {

    public static class BranchObj {
        private int numberOfRecord;

        private Date exchangeDate;

        private String idNameCard;

        private String branchName;

        public BranchObj() {
            super();
        }

        public int getNumberOfRecord() {
            return this.numberOfRecord;
        }

        public void setNumberOfRecord(final int numberOfRecord) {
            this.numberOfRecord = numberOfRecord;
        }

        public Date getExchangeDate() {
            return this.exchangeDate;
        }

        public void setExchangeDate(final Date exchangeDate) {
            this.exchangeDate = exchangeDate;
        }

        public String getIdNameCard() {
            return this.idNameCard;
        }

        public void setIdNameCard(final String idNameCard) {
            this.idNameCard = idNameCard;
        }

        public String getBranchName() {
            return this.branchName;
        }

        public void setBranchName(final String branchName) {
            this.branchName = branchName;
        }
    }

    public static class SiteObj {
        private String zipCode;

        private String addressAll;

        private String addressPref;

        private String addressCity;

        private String addressBlock;

        private String addressBuilding;

        private String idNameCard;

        private Date exchangeDate;

        private int totalRecord;

        private String nameBranch;

        public String getZipCode() {
            return this.zipCode;
        }

        public void setZipCode(final String zipCode) {
            this.zipCode = zipCode;
        }

        public String getAddressAll() {
            return this.addressAll;
        }

        public void setAddressAll(final String addressAll) {
            this.addressAll = addressAll;
        }

        public String getAddressPref() {
            return this.addressPref;
        }

        public void setAddressPref(final String addressPref) {
            this.addressPref = addressPref;
        }

        public String getAddressCity() {
            return this.addressCity;
        }

        public void setAddressCity(final String addressCity) {
            this.addressCity = addressCity;
        }

        public String getAddressBlock() {
            return this.addressBlock;
        }

        public void setAddressBlock(final String addressBlock) {
            this.addressBlock = addressBlock;
        }

        public String getAddressBuilding() {
            return this.addressBuilding;
        }

        public void setAddressBuilding(final String addressBuilding) {
            this.addressBuilding = addressBuilding;
        }

        public String getIdNameCard() {
            return this.idNameCard;
        }

        public void setIdNameCard(final String idNameCard) {
            this.idNameCard = idNameCard;
        }

        public Date getExchangeDate() {
            return this.exchangeDate;
        }

        public void setExchangeDate(final Date exchangeDate) {
            this.exchangeDate = exchangeDate;
        }

        public int getTotalRecord() {
            return this.totalRecord;
        }

        public void setTotalRecord(final int totalRecord) {
            this.totalRecord = totalRecord;
        }

        public String getNameBranch() {
            return this.nameBranch;
        }

        public void setNameBranch(final String nameBranch) {
            this.nameBranch = nameBranch;
        }
    }

    public static class CNumberObj {
        private String idCompany;

        private String tel;

        private String fax;

        private int totalRecord;

        private String idNameCard;

        private Date exchangeDate;

        private String nameBranch;

        public String getIdCompany() {
            return this.idCompany;
        }

        public void setIdCompany(final String idCompany) {
            this.idCompany = idCompany;
        }

        public String getTel() {
            return this.tel;
        }

        public void setTel(final String tel) {
            this.tel = tel;
        }

        public String getFax() {
            return this.fax;
        }

        public void setFax(final String fax) {
            this.fax = fax;
        }

        public int getTotalRecord() {
            return this.totalRecord;
        }

        public void setTotalRecord(final int totolRecord) {
            this.totalRecord = totolRecord;
        }

        public Date getExchangeDate() {
            return this.exchangeDate;
        }

        public void setExchangeDate(final Date exchangeDate) {
            this.exchangeDate = exchangeDate;
        }

        public String getIdNameCard() {
            return this.idNameCard;
        }

        public void setIdNameCard(final String idNameCard) {
            this.idNameCard = idNameCard;
        }

        public String getNameBranch() {
            return this.nameBranch;
        }

        public void setNameBranch(final String nameBranch) {
            this.nameBranch = nameBranch;
        }
    }

    public static class MDomainObj {
        private String idCompany;

        private String emailDomain;

        private Date exchangeDate;

        private int totalRecord;

        public String getIdCompany() {
            return this.idCompany;
        }

        public void setIdCompany(final String idCompany) {
            this.idCompany = idCompany;
        }

        public String getEmailDomain() {
            return this.emailDomain;
        }

        public void setEmailDomain(final String emailDomain) {
            this.emailDomain = emailDomain;
        }

        public Date getExchangeDate() {
            return this.exchangeDate;
        }

        public void setExchangeDate(final Date exchangeDate) {
            this.exchangeDate = exchangeDate;
        }

        public int getTotalRecord() {
            return this.totalRecord;
        }

        public void setTotalRecord(final int totalRecord) {
            this.totalRecord = totalRecord;
        }
    }

    public static class UDomainObj {
        private String idCompany;

        private String urlDomain;

        private Date exchangeDate;

        private int totalRecord;

        public String getIdCompany() {
            return this.idCompany;
        }

        public void setIdCompany(final String idCompany) {
            this.idCompany = idCompany;
        }

        public String getUrlDomain() {
            return this.urlDomain;
        }

        public void setUrlDomain(final String urlDomain) {
            this.urlDomain = urlDomain;
        }

        public Date getExchangeDate() {
            return this.exchangeDate;
        }

        public void setExchangeDate(final Date exchangeDate) {
            this.exchangeDate = exchangeDate;
        }

        public int getTotalRecord() {
            return this.totalRecord;
        }

        public void setTotalRecord(final int totalRecord) {
            this.totalRecord = totalRecord;
        }
    }
}
