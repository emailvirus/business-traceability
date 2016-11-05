package arrow.businesstraceability.control.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import arrow.businesstraceability.cache.entity.FindDataNameCardEntity;
import arrow.businesstraceability.constant.Constants;
import arrow.businesstraceability.control.helper.ScreenContext;
import arrow.businesstraceability.control.service.ElasticSearchService;
import arrow.businesstraceability.control.service.FindDataNameCardService;
import arrow.businesstraceability.persistence.entity.Acc_com_entity;
import arrow.businesstraceability.persistence.entity.San_com_info;
import arrow.businesstraceability.persistence.entity.San_com_live_stat;
import arrow.framework.bean.AbstractBean;
import arrow.framework.util.DateUtils;
import arrow.framework.util.StringUtils;
import arrow.framework.util.collections.CollectionUtils;

/**
 * The Class FindDataNameCard.
 */
@Named
@ViewScoped
public class FindDataNameCardBean extends AbstractBean {

    /** The elastic search service. */
    @Inject
    private ElasticSearchService elasticSearchService;

    /** The find data name card service. */
    @Inject
    private FindDataNameCardService findDataNameCardService;


    /** The input id name card. */
    private String inputIdNameCard;

    /** The input company name. */
    private String inputCompanyName;

    /** The input customer code. */
    private String inputCustomerCode;


    /** The list company. */
    private List<FindDataNameCardEntity> listCompany;

    /** The list name card. */
    private List<FindDataNameCardEntity> listNameCard;

    /** The tab index. */
    private int activeIndex = 0;

    /**
     * Inits the.
     */
    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
        Map<String, Object> attrs = this.screenBean.getCurrentScreenContext().getObjectAttributes();
        if (null == attrs) {
            return;
        }
        this.inputIdNameCard = (String) attrs.get(ScreenContext.SharingDataKey.KEY_WORD_ID_NAME_CARD);
        this.inputCompanyName = (String) attrs.get(ScreenContext.SharingDataKey.KEY_WORD_COMPANY_NAME);
        this.listCompany = (List<FindDataNameCardEntity>) attrs.get(ScreenContext.SharingDataKey.LIST_COMPANY_PROC2);
        this.inputCustomerCode = (String) attrs.get(ScreenContext.SharingDataKey.KEY_WORD_CUSTOMER_CODE);
        this.activeIndex = (int) attrs.get(ScreenContext.SharingDataKey.ACTIVE_INDEX_FIND_ACCOUNTING_DATA);
    }

    /**
     * Search.
     */
    public void search() {
        this.activeIndex = 1;
        List<Map.Entry<Integer, String>> listMapIdAndCompanyName =
            this.elasticSearchService.findAllSanCardDataWithSameIdAndName(this.inputIdNameCard, this.inputCompanyName);
        this.listCompany = new ArrayList<FindDataNameCardEntity>();
        if (listMapIdAndCompanyName.size() != 0) {
            List<Integer> lisIdCompany = new ArrayList<Integer>();
            Iterator<Map.Entry<Integer, String>> itr = listMapIdAndCompanyName.iterator();
            if (StringUtils.isNotEmpty(this.inputIdNameCard)) {
                while (itr.hasNext()) {
                    Map.Entry<Integer, String> element = itr.next();
                    int idCompanyInListSan = element.getKey();
                    if (lisIdCompany.contains(idCompanyInListSan)) {
                        itr.remove();
                    } else {
                        lisIdCompany.add(idCompanyInListSan);
                    }
                }
            } else {
                while (itr.hasNext()) {
                    Map.Entry<Integer, String> element = itr.next();
                    lisIdCompany.add(element.getKey());
                }
            }
            Map<Integer, San_com_live_stat> listSanComStatOfCompany =
                this.elasticSearchService.getListSanComLiveStatByListIdCompany(lisIdCompany);
            if (listSanComStatOfCompany == null) {
                return;
            }
            for (int i = 0; i < listMapIdAndCompanyName.size(); i++) {
                this.initDataForListCompany(listSanComStatOfCompany, listMapIdAndCompanyName.get(i).getKey(),
                    listMapIdAndCompanyName.get(i).getValue(), lisIdCompany.get(i));
            }
        }

    }

    /**
     * Switch to input accounting data.
     */
    public void switchToInputAccountingData() {
        this.listNameCard.get(0).setSelected(false);
        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(ScreenContext.SharingDataKey.ID_INT_SAN_COMPANY, this.listNameCard.get(0).getIdCompany());
        attrs.put(ScreenContext.SharingDataKey.LIST_COMPANY_PROC2, this.getListCard());
        attrs.put(ScreenContext.SharingDataKey.KEY_WORD_ID_NAME_CARD, this.inputIdNameCard);
        attrs.put(ScreenContext.SharingDataKey.KEY_WORD_COMPANY_NAME, this.inputCompanyName);
        attrs.put(ScreenContext.SharingDataKey.KEY_WORD_CUSTOMER_CODE, this.inputCustomerCode);
        attrs.put(ScreenContext.SharingDataKey.ACTIVE_INDEX_FIND_ACCOUNTING_DATA, this.activeIndex);
        this.screenBean.switchScreenWithObjectData(ScreenContext.INPUT_ACCOUNTING_DATA, true, attrs);
    }

    /**
     * Search by customer code.
     */
    public void searchByCustomerCode() {
        this.activeIndex = 0;
        this.listCompany = new ArrayList<FindDataNameCardEntity>();
        Acc_com_entity resultFindByCustomerCode =
            this.findDataNameCardService.getComEntityByCustomerCode(this.inputCustomerCode.concat(
                Constants.CUSTOMER_CODE_SUFFIX).toUpperCase());
        if ((resultFindByCustomerCode == null) || (resultFindByCustomerCode.getId_int_san_company() == null)) {
            return;
        }
        List<Integer> lisIdCompany = new ArrayList<Integer>();
        lisIdCompany.add(resultFindByCustomerCode.getId_int_san_company());
        Map<Integer, San_com_live_stat> listSanComStatOfCompany =
            this.elasticSearchService.getListSanComLiveStatByListIdCompany(lisIdCompany);
        if (listSanComStatOfCompany == null) {
            return;
        }
        San_com_info comInfo = this.findDataNameCardService.getSanComInfoByIdCompany(lisIdCompany.get(0));
        this.initDataForListCompany(listSanComStatOfCompany, resultFindByCustomerCode.getId_int_san_company(),
            comInfo.getName_company(), resultFindByCustomerCode.getId_int_san_company());
    }

    /**
     * Inits the data for list company.
     *
     * @param listSanComStatOfCompany the list san com stat of company
     * @param idIntSanCompany the id int san company
     * @param nameCompany the name company
     * @param countUrlOfCompany the count url of company
     */
    private void initDataForListCompany(final Map<Integer, San_com_live_stat> listSanComStatOfCompany,
        final Integer idIntSanCompany, final String nameCompany, final Integer countUrlOfCompany) {
        FindDataNameCardEntity temp = new FindDataNameCardEntity();
        San_com_live_stat currentSanComLiveStat = listSanComStatOfCompany.get(idIntSanCompany);
        if (currentSanComLiveStat != null) {
            temp.setCompanyName(nameCompany);
            temp.setCountUrlOfCompany(this.findDataNameCardService
                .countNumberUrlOfCompanyByIdCompany(countUrlOfCompany));
            temp.setSumOfCard(currentSanComLiveStat.getSn_card());
            temp.setSumOfOwner(currentSanComLiveStat.getSn_owner());
            temp.setSumOfClient(currentSanComLiveStat.getSn_client());
            temp.setLatestChanged(DateUtils.formatDate(currentSanComLiveStat.getDate_latest_exchange()));
            temp.setOldestChanged(DateUtils.formatDate(currentSanComLiveStat.getDate_oldest_exchange()));
            temp.setRegularContact(currentSanComLiveStat.getCn_cp());
            temp.setFreqOfAccess(currentSanComLiveStat.getCn_vp());
            temp.setIdCompany(currentSanComLiveStat.getId_company());
            this.listCompany.add(temp);
        }
    }

    /**
     * get list card in table.
     *
     * @return the list card
     */
    public List<FindDataNameCardEntity> getListCard() {
        return this.listCompany;
    }

    /**
     * Gets the input id name card.
     *
     * @return the input id name card
     */
    public String getInputIdNameCard() {
        return this.inputIdNameCard;
    }

    /**
     * Sets the input id name card.
     *
     * @param inputIdNameCard the new input id name card
     */
    public void setInputIdNameCard(final String inputIdNameCard) {
        this.inputIdNameCard = inputIdNameCard;
    }

    /**
     * Gets the input company name.
     *
     * @return the input company name
     */
    public String getInputCompanyName() {
        return this.inputCompanyName;
    }

    /**
     * Sets the input company name.
     *
     * @param inputCompanyName the new input company name
     */
    public void setInputCompanyName(final String inputCompanyName) {
        this.inputCompanyName = inputCompanyName;
    }

    /**
     * Gets the input customer code.
     *
     * @return the input customer code
     */
    public String getInputCustomerCode() {
        return this.inputCustomerCode;
    }

    /**
     * Sets the input customer code.
     *
     * @param inputCustomerCode the new input customer code
     */
    public void setInputCustomerCode(final String inputCustomerCode) {
        this.inputCustomerCode = inputCustomerCode;
    }

    /**
     * Gets the list name card.
     *
     * @return the list name card
     */
    public List<FindDataNameCardEntity> getListNameCard() {
        if (CollectionUtils.isEmpty(this.listNameCard)) {
            this.listNameCard = new ArrayList<FindDataNameCardEntity>();
        }
        return this.listNameCard;
    }

    /**
     * Sets the list name card.
     *
     * @param listNameCard the new list name card
     */
    public void setListNameCard(final List<FindDataNameCardEntity> listNameCard) {
        this.listNameCard = listNameCard;
    }

    /**
     * Gets the tab index.
     *
     * @return the tab index
     */
    public int getTabIndex() {
        return this.activeIndex;
    }

    /**
     * Sets the tab index.
     *
     * @param tabIndex the new tab index
     */
    public void setTabIndex(final int tabIndex) {
        this.activeIndex = tabIndex;
    }


}
