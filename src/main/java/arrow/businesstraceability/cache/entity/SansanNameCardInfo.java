package arrow.businesstraceability.cache.entity;

import org.apache.commons.lang3.StringUtils;

import arrow.businesstraceability.constant.SansanConstants.NameCardInfoInputRules;

/**
 * The Class SansanNameCardInfo.
 */
public class SansanNameCardInfo {

    /** The name company. */
    private String nameCompany;

    /** The name com eng. */
    private String nameComEng;

    /** The name com kana. */
    private String nameComKana;

    /** The name branch. */
    private String nameBranch;

    /** The position. */
    private String position;

    /** The name client person. */
    private String nameClientPerson;

    /** The name cl first. */
    private String nameClFirst;

    /** The name cl last. */
    private String nameClLast;

    /** The name cl kana. */
    private String nameClKana;

    /** The name cl first kana. */
    private String nameClFirstKana;

    /** The name cl last kana. */
    private String nameClLastKana;

    /** The code zip. */
    private String codeZip;

    /** The addr all. */
    private String addrAll;

    /** The addr pref. */
    private String addrPref;

    /** The addr city. */
    private String addrCity;

    /** The addr block. */
    private String addrBlock;

    /** The addr bldg. */
    private String addrBldg;

    /** The tel11. */
    private String tel11;

    /** The tel12. */
    private String tel12;

    /** The fax. */
    private String fax;

    /** The mphone. */
    private String mphone;

    /** The email. */
    private String email;

    /** The email name. */
    private String emailName;

    /** The email domain. */
    private String emailDomain;

    /** The url. */
    private String url;

    /** The url domain. */
    private String urlDomain;

    /** The zipcode2. */
    private String zipcode2;

    /** The addr all2. */
    private String addrAll2;

    /** The addr pref2. */
    private String addrPref2;

    /** The addr city2. */
    private String addrCity2;

    /** The addr block2. */
    private String addrBlock2;

    /** The addr bldg2. */
    private String addrBldg2;

    /** The tel21. */
    private String tel21;

    /** The tel22. */
    private String tel22;

    /** The fax2. */
    private String fax2;

    /** The mphone2. */
    private String mphone2;

    /** The email2. */
    private String email2;

    /** The email name2. */
    private String emailName2;

    /** The email domain2. */
    private String emailDomain2;

    /** The url2. */
    private String url2;

    /** The url domain2. */
    private String urlDomain2;

    /** The pcategory. */
    private String pcategory;

    /** The vp. */
    private String vp;

    /** The cp. */
    private String cp;

    /** The fileout. */
    private String fileout;

    /** The tags. */
    private String tags;

    /** The date register. */
    private String dateRegister;

    /** The date exchange. */
    private String dateExchange;

    /** The flg udelivery addr. */
    private String flgUdeliveryAddr;

    /** The flg udelivery tel. */
    private String flgUdeliveryTel;

    /** The flg udelivery fax. */
    private String flgUdeliveryFax;

    /** The flg udelivery email. */
    private String flgUdeliveryEmail;

    /** The memo. */
    private String memo;

    /** The automemo. */
    private String automemo;

    /** The ac user. */
    private String acUser;

    /** The name user. */
    private String nameUser;

    /** The cont date. */
    private String contDate;

    /** The cont cat. */
    private String contCat;

    /** The cont title. */
    private String contTitle;

    /** The cont location. */
    private String contLocation;

    /** The cont memo. */
    private String contMemo;

    /** The id person. */
    private String idPerson;

    /** The id card. */
    private String idCard;

    /** The id company. */
    private String idCompany;


    public java.lang.String getNameCompany() {
        return StringUtils.isEmpty(this.nameCompany) ? this.nameCompany : this.nameCompany.substring(0,
            Math.min(NameCardInfoInputRules.NAME_COMPANY_LENGTH, this.nameCompany.length()));
    }

    public void setNameCompany(final java.lang.String nameCompany) {
        this.nameCompany = nameCompany;
    }

    public java.lang.String getNameComEng() {
        return StringUtils.isEmpty(this.nameComEng) ? this.nameComEng : this.nameComEng.substring(0,
            Math.min(NameCardInfoInputRules.NAME_COM_ENG_LENGTH, this.nameComEng.length()));
    }

    public void setNameComEng(final java.lang.String nameComEng) {
        this.nameComEng = nameComEng;
    }

    public java.lang.String getNameComKana() {
        return StringUtils.isEmpty(this.nameComKana) ? this.nameComKana : this.nameComKana.substring(0,
            Math.min(NameCardInfoInputRules.NAME_COM_KANA_LENGTH, this.nameComKana.length()));
    }

    public void setNameComKana(final java.lang.String nameComKana) {
        this.nameComKana = nameComKana;
    }

    public java.lang.String getNameBranch() {
        return StringUtils.isEmpty(this.nameBranch) ? this.nameBranch : this.nameBranch.substring(0,
            Math.min(NameCardInfoInputRules.NAME_BRANCH_LENGTH, this.nameBranch.length()));
    }

    public void setNameBranch(final java.lang.String nameBranch) {
        this.nameBranch = nameBranch;
    }

    public java.lang.String getPosition() {
        return StringUtils.isEmpty(this.position) ? this.position : this.position.substring(0,
            Math.min(NameCardInfoInputRules.POSITION_LENGTH, this.position.length()));
    }

    public void setPosition(final java.lang.String position) {
        this.position = position;
    }

    public java.lang.String getNameClientPerson() {
        return this.nameClientPerson;
    }

    public void setNameClientPerson(final java.lang.String nameClientPerson) {
        this.nameClientPerson = nameClientPerson;
    }

    public java.lang.String getNameClFirst() {
        return StringUtils.isEmpty(this.nameClFirst) ? this.nameClFirst : this.nameClFirst.substring(0,
            Math.min(NameCardInfoInputRules.NAME_CL_FIRST_LENGTH, this.nameClFirst.length()));
    }

    public void setNameClFirst(final java.lang.String nameClFirst) {
        this.nameClFirst = nameClFirst;
    }

    public java.lang.String getNameClLast() {
        return StringUtils.isEmpty(this.nameClLast) ? this.nameClLast : this.nameClLast.substring(0,
            Math.min(NameCardInfoInputRules.NAME_CL_LAST_LENGTH, this.nameClLast.length()));
    }

    public void setNameClLast(final java.lang.String nameClLast) {
        this.nameClLast = nameClLast;
    }

    public java.lang.String getNameClKana() {
        return this.nameClKana;
    }

    public void setNameClKana(final java.lang.String nameClKana) {
        this.nameClKana = nameClKana;
    }

    public java.lang.String getNameClFirstKana() {
        return StringUtils.isEmpty(this.nameClFirstKana) ? this.nameClFirstKana : this.nameClFirstKana.substring(0,
            Math.min(NameCardInfoInputRules.NAME_CL_FIRST_KANA_LENGTH, this.nameClFirstKana.length()));
    }

    public void setNameClFirstKana(final java.lang.String nameClFirstKana) {
        this.nameClFirstKana = nameClFirstKana;
    }

    public java.lang.String getNameClLastKana() {
        return StringUtils.isEmpty(this.nameClLastKana) ? this.nameClLastKana : this.nameClLastKana.substring(0,
            Math.min(NameCardInfoInputRules.NAME_CL_LAST_KANA_LENGTH, this.nameClLastKana.length()));
    }

    public void setNameClLastKana(final java.lang.String nameClLastKana) {
        this.nameClLastKana = nameClLastKana;
    }

    public java.lang.String getCodeZip() {
        return StringUtils.isEmpty(this.codeZip) ? this.codeZip : this.codeZip.substring(0,
            Math.min(NameCardInfoInputRules.CODE_ZIP_LENGTH, this.codeZip.length()));
    }

    public void setCodeZip(final java.lang.String codeZip) {
        this.codeZip = codeZip;
    }

    public java.lang.String getAddrAll() {
        return StringUtils.isEmpty(this.addrAll) ? this.addrAll : this.addrAll.substring(0,
            Math.min(NameCardInfoInputRules.ADDR_ALL_LENGTH, this.addrAll.length()));
    }

    public void setAddrAll(final java.lang.String addrAll) {
        this.addrAll = addrAll;
    }

    public java.lang.String getAddrPref() {
        return this.addrPref;
    }

    public void setAddrPref(final java.lang.String addrPref) {
        this.addrPref = addrPref;
    }

    public java.lang.String getAddrCity() {
        return this.addrCity;
    }

    public void setAddrCity(final java.lang.String addrCity) {
        this.addrCity = addrCity;
    }

    public java.lang.String getAddrBlock() {
        return this.addrBlock;
    }

    public void setAddrBlock(final java.lang.String addrBlock) {
        this.addrBlock = addrBlock;
    }

    public java.lang.String getAddrBldg() {
        return this.addrBldg;
    }

    public void setAddrBldg(final java.lang.String addrBldg) {
        this.addrBldg = addrBldg;
    }

    public java.lang.String getTel11() {
        return StringUtils.isEmpty(this.tel11) ? this.tel11 : this.tel11.substring(0,
            Math.min(NameCardInfoInputRules.TEL11_LENGTH, this.tel11.length()));
    }

    public void setTel11(final java.lang.String tel11) {
        this.tel11 = tel11;
    }

    public java.lang.String getTel12() {
        return StringUtils.isEmpty(this.tel12) ? this.tel12 : this.tel12.substring(0,
            Math.min(NameCardInfoInputRules.TEL12_LENGTH, this.tel12.length()));
    }

    public void setTel12(final java.lang.String tel12) {
        this.tel12 = tel12;
    }

    public java.lang.String getFax() {
        return StringUtils.isEmpty(this.fax) ? this.fax : this.fax.substring(0,
            Math.min(NameCardInfoInputRules.FAX_LENGTH, this.fax.length()));
    }

    public void setFax(final java.lang.String fax) {
        this.fax = fax;
    }

    public java.lang.String getMphone() {
        return StringUtils.isEmpty(this.mphone) ? this.mphone : this.mphone.substring(0,
            Math.min(NameCardInfoInputRules.MPHONE_LENGTH, this.mphone.length()));
    }

    public void setMphone(final java.lang.String mphone) {
        this.mphone = mphone;
    }

    public java.lang.String getEmail() {
        return StringUtils.isEmpty(this.email) ? this.email : this.email.substring(0,
            Math.min(NameCardInfoInputRules.EMAIL_LENGTH, this.email.length()));
    }

    public void setEmail(final java.lang.String email) {
        this.email = email;
    }

    public java.lang.String getEmailName() {
        this.emailName = this.getEmailName(this.email);
        return this.emailName;
    }

    public void setEmailName(final java.lang.String emailName) {
        this.emailName = emailName;
    }

    public java.lang.String getEmailDomain() {
        this.emailDomain = this.getEmailDomain(this.email);
        return this.emailDomain;
    }

    public void setEmailDomain(final java.lang.String emailDomain) {
        this.emailDomain = emailDomain;
    }

    public java.lang.String getUrl() {
        return StringUtils.isEmpty(this.url) ? this.url : this.url.substring(0,
            Math.min(NameCardInfoInputRules.URL_LENGTH, this.url.length()));
    }

    public void setUrl(final java.lang.String url) {
        this.url = url;
    }

    public java.lang.String getUrlDomain() {
        this.urlDomain = this.getUrlDomain(this.url);
        return this.urlDomain;
    }

    public void setUrlDomain(final java.lang.String urlDomain) {
        this.urlDomain = urlDomain;
    }

    public java.lang.String getZipcode2() {
        return StringUtils.isEmpty(this.zipcode2) ? this.zipcode2 : this.zipcode2.substring(0,
            Math.min(NameCardInfoInputRules.ZIPCODE2_LENGTH, this.zipcode2.length()));
    }

    public void setZipcode2(final java.lang.String zipcode2) {
        this.zipcode2 = zipcode2;
    }

    public java.lang.String getAddrAll2() {
        return StringUtils.isEmpty(this.addrAll2) ? this.addrAll2 : this.addrAll2.substring(0,
            Math.min(NameCardInfoInputRules.ADDR_ALL2_LENGTH, this.addrAll2.length()));
    }

    public void setAddrAll2(final java.lang.String addrAll2) {
        this.addrAll2 = addrAll2;
    }

    public java.lang.String getAddrPref2() {
        return this.addrPref2;
    }

    public void setAddrPref2(final java.lang.String addrPref2) {
        this.addrPref2 = addrPref2;
    }

    public java.lang.String getAddrCity2() {
        return this.addrCity2;
    }

    public void setAddrCity2(final java.lang.String addrCity2) {
        this.addrCity2 = addrCity2;
    }

    public java.lang.String getAddrBlock2() {
        return this.addrBlock2;
    }

    public void setAddrBlock2(final java.lang.String addrBlock2) {
        this.addrBlock2 = addrBlock2;
    }

    public java.lang.String getAddrBldg2() {
        return this.addrBldg2;
    }

    public void setAddrBldg2(final java.lang.String addrBldg2) {
        this.addrBldg2 = addrBldg2;
    }

    public java.lang.String getTel21() {
        return StringUtils.isEmpty(this.tel21) ? this.tel21 : this.tel21.substring(0,
            Math.min(NameCardInfoInputRules.TEL21_LENGTH, this.tel21.length()));
    }

    public void setTel21(final java.lang.String tel21) {
        this.tel21 = tel21;
    }

    public java.lang.String getTel22() {
        return StringUtils.isEmpty(this.tel22) ? this.tel22 : this.tel22.substring(0,
            Math.min(NameCardInfoInputRules.TEL22_LENGTH, this.tel22.length()));
    }

    public void setTel22(final java.lang.String tel22) {
        this.tel22 = tel22;
    }

    public java.lang.String getFax2() {
        return StringUtils.isEmpty(this.fax2) ? this.fax2 : this.fax2.substring(0,
            Math.min(NameCardInfoInputRules.FAX2_LENGTH, this.fax2.length()));
    }

    public void setFax2(final java.lang.String fax2) {
        this.fax2 = fax2;
    }

    public java.lang.String getMphone2() {
        return StringUtils.isEmpty(this.mphone2) ? this.mphone2 : this.mphone2.substring(0,
            Math.min(NameCardInfoInputRules.MPHONE2_LENGTH, this.mphone2.length()));
    }

    public void setMphone2(final java.lang.String mphone2) {
        this.mphone2 = mphone2;
    }

    public java.lang.String getEmail2() {
        return StringUtils.isEmpty(this.email2) ? this.email2 : this.email2.substring(0,
            Math.min(NameCardInfoInputRules.EMAIL2_LENGTH, this.email2.length()));
    }

    public void setEmail2(final java.lang.String email2) {
        this.email2 = email2;
    }

    public java.lang.String getEmailName2() {
        this.emailName2 = this.getEmailName(this.email2);
        return this.emailName2;
    }

    public void setEmailName2(final java.lang.String emailName2) {
        this.emailName2 = emailName2;
    }

    public java.lang.String getEmailDomain2() {
        this.emailDomain2 = this.getEmailDomain(this.email2);
        return this.emailDomain2;
    }

    public void setEmailDomain2(final java.lang.String emailDomain2) {
        this.emailDomain2 = emailDomain2;
    }

    public java.lang.String getUrl2() {
        return StringUtils.isEmpty(this.url2) ? this.url2 : this.url2.substring(0,
            Math.min(NameCardInfoInputRules.URL2_LENGTH, this.url2.length()));
    }

    public void setUrl2(final java.lang.String url2) {
        this.url2 = url2;
    }

    public java.lang.String getUrlDomain2() {
        this.urlDomain2 = this.getUrlDomain(this.url2);
        return this.urlDomain2;
    }

    public void setUrlDomain2(final java.lang.String urlDomain2) {
        this.urlDomain2 = urlDomain2;
    }

    public java.lang.String getPcategory() {
        return this.pcategory;
    }

    public void setPcategory(final java.lang.String pcategory) {
        this.pcategory = pcategory;
    }

    public java.lang.String getVp() {
        return this.vp;
    }

    public void setVp(final java.lang.String vp) {
        this.vp = vp;
    }

    public java.lang.String getCp() {
        return this.cp;
    }

    public void setCp(final java.lang.String cp) {
        this.cp = cp;
    }

    public java.lang.String getFileout() {
        return this.fileout;
    }

    public void setFileout(final java.lang.String fileout) {
        this.fileout = fileout;
    }

    public java.lang.String getTags() {
        return this.tags;
    }

    public void setTags(final java.lang.String tags) {
        this.tags = tags;
    }

    public java.lang.String getDateRegister() {
        return this.dateRegister;
    }

    public void setDateRegister(final java.lang.String dateRegister) {
        this.dateRegister = dateRegister;
    }

    public java.lang.String getDateExchange() {
        return this.dateExchange;
    }

    public void setDateExchange(final java.lang.String dateExchange) {
        this.dateExchange = dateExchange;
    }

    public java.lang.String getFlgUdeliveryAddr() {
        return this.flgUdeliveryAddr;
    }

    public void setFlgUdeliveryAddr(final java.lang.String flgUdeliveryAddr) {
        this.flgUdeliveryAddr = flgUdeliveryAddr;
    }

    public java.lang.String getFlgUdeliveryTel() {
        return this.flgUdeliveryTel;
    }

    public void setFlgUdeliveryTel(final java.lang.String flgUdeliveryTel) {
        this.flgUdeliveryTel = flgUdeliveryTel;
    }

    public java.lang.String getFlgUdeliveryFax() {
        return this.flgUdeliveryFax;
    }

    public void setFlgUdeliveryFax(final java.lang.String flgUdeliveryFax) {
        this.flgUdeliveryFax = flgUdeliveryFax;
    }

    public java.lang.String getFlgUdeliveryEmail() {
        return this.flgUdeliveryEmail;
    }

    public void setFlgUdeliveryEmail(final java.lang.String flgUdeliveryEmail) {
        this.flgUdeliveryEmail = flgUdeliveryEmail;
    }

    public java.lang.String getMemo() {
        return this.memo;
    }

    public void setMemo(final java.lang.String memo) {
        this.memo = memo;
    }

    public java.lang.String getAutomemo() {
        return this.automemo;
    }

    public void setAutomemo(final java.lang.String automemo) {
        this.automemo = automemo;
    }

    public java.lang.String getAcUser() {
        return this.acUser;
    }

    public void setAcUser(final java.lang.String acUser) {
        this.acUser = acUser;
    }

    public java.lang.String getNameUser() {
        return this.nameUser;
    }

    public void setNameUser(final java.lang.String nameUser) {
        this.nameUser = nameUser;
    }

    public java.lang.String getContDate() {
        return this.contDate;
    }

    public void setContDate(final java.lang.String contDate) {
        this.contDate = contDate;
    }

    public java.lang.String getContCat() {
        return this.contCat;
    }

    public void setContCat(final java.lang.String contCat) {
        this.contCat = contCat;
    }

    public java.lang.String getContTitle() {
        return this.contTitle;
    }

    public void setContTitle(final java.lang.String contTitle) {
        this.contTitle = contTitle;
    }

    public java.lang.String getContLocation() {
        return this.contLocation;
    }

    public void setContLocation(final java.lang.String contLocation) {
        this.contLocation = contLocation;
    }

    public java.lang.String getContMemo() {
        return this.contMemo;
    }

    public void setContMemo(final java.lang.String contMemo) {
        this.contMemo = contMemo;
    }

    public java.lang.String getIdPerson() {
        return this.idPerson;
    }

    public void setIdPerson(final java.lang.String idPerson) {
        this.idPerson = idPerson;
    }

    public java.lang.String getIdCard() {
        return this.idCard;
    }

    public void setIdCard(final java.lang.String idCard) {
        this.idCard = idCard;
    }

    public java.lang.String getIdCompany() {
        return this.idCompany;
    }

    public void setIdCompany(final java.lang.String idCompany) {
        this.idCompany = idCompany;
    }

    private String getEmailName(final String email) {
        if (StringUtils.isEmpty(email)) {
            return null;
        }
        int index = email.indexOf("@");
        return email.substring(0, index);
    }

    private String getEmailDomain(final String email) {
        if (StringUtils.isEmpty(email)) {
            return null;
        }
        int index = email.indexOf("@");
        return email.substring(index + 1, email.length());
    }

    private String getUrlDomain(final String url) {
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        return url.replaceFirst("http://", "").replaceFirst("https://", "");
    }

}
