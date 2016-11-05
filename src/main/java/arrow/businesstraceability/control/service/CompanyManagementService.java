package arrow.businesstraceability.control.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.context.ConversationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.primefaces.model.DualListModel;

import arrow.businesstraceability.base.AbstractService;
import arrow.businesstraceability.constant.Constants;
import arrow.businesstraceability.control.bean.UserCredential;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.persistence.dto.Company_mst_DTO;
import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Basepoint_info;
import arrow.businesstraceability.persistence.entity.Company_mst;
import arrow.businesstraceability.persistence.entity.Customer_info_view;
import arrow.businesstraceability.persistence.entity.Industry_big_mst;
import arrow.businesstraceability.persistence.mapped.Addresspoint_mst_MAPPED_;
import arrow.businesstraceability.persistence.mapped.Company_mst_MAPPED_;
import arrow.businesstraceability.persistence.repository.Addresspoint_mstRepository;
import arrow.businesstraceability.persistence.repository.Basepoint_infoRepository;
import arrow.businesstraceability.persistence.repository.Company_mstRepository;
import arrow.businesstraceability.persistence.repository.Customer_info_viewRepository;
import arrow.businesstraceability.search.ExcelUtils;
import arrow.businesstraceability.util.ArrowStringUtils;
import arrow.businesstraceability.util.SelectItemGenerator;
import arrow.businesstraceability.util.SelectItemGenerator.ListType;
import arrow.framework.faces.message.ErrorMessage;
import arrow.framework.faces.message.InfoMessage;
import arrow.framework.faces.message.Message;
import arrow.framework.faces.util.BeanCopier;
import arrow.framework.persistence.ArrowQuery;
import arrow.framework.persistence.ArrowQuery.ResultList.FilterType;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.StringUtils;
import arrow.framework.util.cdi.Instance;
import arrow.framework.util.collections.CollectionUtils;


/**
 * The Class CompanyManagementService.
 */
@Named
@ConversationScoped
public class CompanyManagementService extends AbstractService {

    /** The Constant MAP_EXPORT_BUSINESS_COMPANY_CODE. */
    static final Map<String, String> MAP_EXPORT_BUSINESS_COMPANY_CODE = new HashMap<String, String>();

    /** The Constant MAP_COLOR_BACKGROUND. */
    static final Map<String, Short> MAP_COLOR_BACKGROUND = new HashMap<String, Short>();

    /** The Constant LIST_KEY_EXPORT_BUSSINESS_COMPANY_CODE. */
    static final List<String> LIST_KEY_EXPORT_BUSSINESS_COMPANY_CODE =
        Collections.unmodifiableList(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"));

    /** The Constant NUMBER_ROWS_OF_BLOCK. */
    private static final Integer NUMBER_ROWS_OF_BLOCK = 200;

    /** The Constant START_ROW_INDEX. */
    private static final Integer START_ROW_INDEX = 3;

    /** The Constant ROW_CATEGORY. */
    private static final Integer ROW_CATEGORY = 25;

    /** The Constant ROW_CATEGORY_NEXT. */
    private static final Integer ROW_CATEGORY_NEXT = 50;

    /** The Constant COLUMN_CATEGORY. */
    private static final Integer COLUMN_CATEGORY = 0;

    /** The Constant COLUMN_CUSTOMER_CODE. */
    private static final Integer COLUMN_CUSTOMER_CODE = 1;

    /** The Constant COLUMN_COMPANY_NAME. */
    private static final Integer COLUMN_COMPANY_NAME = 2;

    /** The Constant COLUMN_START_BORDER. */
    private static final Integer COL_START_BORDER = 3;

    /** The Constant COLUMN_END_BORDER. */
    private static final Integer COL_END_BORDER = 22;

    /** The Constant FILE_NAME. */
    private static final String FILE_NAME = "取引先一覧.xls";

    /** The Constant TEMPLATE_DIR. */
    private static final String TEMPLATE_DIR = "/excel-template";

    /** The Constant NUMBER_TWO. */
    private static final Integer NUMBER_TWO = 2;

    /** The Constant ONE_ZERO. */
    private static final String ONE_ZERO = "0";

    /** The Constant TWO_ZERO. */
    private static final String TWO_ZERO = "00";

    /** The Constant START_INDEX_OF_NUMBERING. */
    private static final int START_INDEX_OF_NUMBERING = 2;

    /** The Constant NUMBERING_FORMAT. */
    private static final String NUMBERING_FORMAT = "%1$08d";

    static {
        CompanyManagementService.MAP_EXPORT_BUSINESS_COMPANY_CODE.put("A", "あ行");
        CompanyManagementService.MAP_EXPORT_BUSINESS_COMPANY_CODE.put("B", "か行");
        CompanyManagementService.MAP_EXPORT_BUSINESS_COMPANY_CODE.put("C", "さ行");
        CompanyManagementService.MAP_EXPORT_BUSINESS_COMPANY_CODE.put("D", "た行");
        CompanyManagementService.MAP_EXPORT_BUSINESS_COMPANY_CODE.put("E", "な行");
        CompanyManagementService.MAP_EXPORT_BUSINESS_COMPANY_CODE.put("F", "は行");
        CompanyManagementService.MAP_EXPORT_BUSINESS_COMPANY_CODE.put("G", "ま行");
        CompanyManagementService.MAP_EXPORT_BUSINESS_COMPANY_CODE.put("H", "や行");
        CompanyManagementService.MAP_EXPORT_BUSINESS_COMPANY_CODE.put("I", "ら行");
        CompanyManagementService.MAP_EXPORT_BUSINESS_COMPANY_CODE.put("J", "わ行");
    }

    static {
        CompanyManagementService.MAP_COLOR_BACKGROUND.put("A", Short.valueOf("47"));
        CompanyManagementService.MAP_COLOR_BACKGROUND.put("B", Short.valueOf("43"));
        CompanyManagementService.MAP_COLOR_BACKGROUND.put("C", Short.valueOf("42"));
        CompanyManagementService.MAP_COLOR_BACKGROUND.put("D", Short.valueOf("44"));
        CompanyManagementService.MAP_COLOR_BACKGROUND.put("E", Short.valueOf("50"));
        CompanyManagementService.MAP_COLOR_BACKGROUND.put("F", Short.valueOf("52"));
        CompanyManagementService.MAP_COLOR_BACKGROUND.put("G", Short.valueOf("53"));
        CompanyManagementService.MAP_COLOR_BACKGROUND.put("H", Short.valueOf("48"));
        CompanyManagementService.MAP_COLOR_BACKGROUND.put("I", Short.valueOf("45"));
        CompanyManagementService.MAP_COLOR_BACKGROUND.put("J", Short.valueOf("51"));
    }

    /** The repo. */
    @Inject
    private Company_mstRepository repo;

    /** The addresspoint service. */
    @Inject
    private AddressPointService addresspointService;

    /** The industry service. */
    @Inject
    private IndustryService industryService;

    /** The user credential. */
    @Inject
    private UserCredential userCredential;

    /** The all addresspoints. */
    private List<Addresspoint_mst> allAddresspoints;

    /** The basepoint info repository. */
    @Inject
    private Basepoint_infoRepository basepointInfoRepository;

    /** The address repo. */
    @Inject
    private Addresspoint_mstRepository addressRepo;

    @Inject
    private Customer_info_viewRepository customerInfoRepo;

    /**
     * get all Companies for address point code.
     *
     * @param adpCode the adp code
     * @return the company code for auto numberring
     */
    public List<Company_mst> getCompanyCodeForAutoNumberring(final String adpCode) {
        final StringBuilder query = new StringBuilder();
        query.append("FROM Company_mst WHERE com_company_code LIKE :adpCode ORDER BY com_company_code DESC");
        return super.emMain.createQuery(query.toString(), Company_mst.class).setParameter("adpCode", adpCode + "%")
            .getResultList();
    }

    /**
     * get All company for export excel.
     *
     * @author lehoangngochan
     * @return list company
     */
    public List<Company_mst> getCompanyForExportExcel() {
        final StringBuilder query = new StringBuilder();
        query.append("FROM Company_mst WHERE com_delete_flg = 'false' ORDER BY com_customer_code ASC");
        return EmLocator.getEm().createQuery(query.toString(), Company_mst.class).getResultList();
    }

    /**
     * Search.
     *
     * @param companyName the company name
     * @param addresspointMst the addresspoint_mst
     * @param companyField the company field
     * @param listedCode the listed code
     * @return the list
     */
    public List<Company_mst> search(final String companyName, final Addresspoint_mst addresspointMst,
        final Industry_big_mst companyField, final String listedCode) {

        ArrowQuery<Company_mst> query = new ArrowQuery<Company_mst>(super.emMain);
        query.select("e").from("Company_mst e");
        query.orderBy("e.com_company_code DESC");
        if (StringUtils.isNotEmpty(companyName)) {
            query.where("UPPER(e.com_company_name) LIKE ? OR UPPER(e.com_company_furigana) LIKE ?",
                StringUtils.likePattern(companyName), StringUtils.likePattern(companyName));
        }
        if (addresspointMst != null) {
            query.where("e.com_point_code=?", addresspointMst.getAdp_code());
        }
        if (companyField != null) {
            query.where("e.com_indbig_code = ?", companyField.getBig_code());
        }
        if (arrow.framework.util.StringUtils.isNotEmpty(listedCode)) {
            query.where("e.com_listed_code=?", listedCode.toUpperCase());
        }
        return query.getResultList();
    }

    /**
     * Search company.
     *
     * @param companyName the company name
     * @return the list
     */
    public List<Company_mst> searchCompany(final String companyName) {
        final CriteriaBuilder builder = super.emMain.getCriteriaBuilder();
        final CriteriaQuery<Company_mst> query = builder.createQuery(Company_mst.class);
        final Root<Company_mst> companyMst = query.from(Company_mst.class);
        final Join<Company_mst, Addresspoint_mst> addressPointMst =
            companyMst.join(Company_mst_MAPPED_.addresspoint_mst);
        query.select(companyMst);
        query.orderBy(builder.desc(companyMst.get(Company_mst_MAPPED_.com_company_code)));

        Predicate whereCondition;
        // condition
        if (!arrow.framework.util.StringUtils.isEmpty(companyName)) {
            whereCondition =
                builder.and(builder.like(builder.upper(companyMst.get(Company_mst_MAPPED_.com_company_name)),
                    arrow.framework.util.StringUtils.buildLikeValueExpression(companyName.toUpperCase())));

            whereCondition = builder.or(whereCondition,
                builder.like(builder.upper(companyMst.get(Company_mst_MAPPED_.com_company_code)),
                    arrow.framework.util.StringUtils.buildLikeValueExpression(companyName.toUpperCase())));

            whereCondition = builder.or(whereCondition,
                builder.like(builder.upper(addressPointMst.get(Addresspoint_mst_MAPPED_.adp_name)),
                    arrow.framework.util.StringUtils.buildLikeValueExpression(companyName.toUpperCase())));
        } else {
            whereCondition = builder.conjunction();
        }

        if (whereCondition != null) {
            query.where(whereCondition);
        }
        return this.emMain.createQuery(query).setMaxResults(Constants.MAX_RESULT_SEARCH_COMPANY).getResultList();
    }

    /**
     * save company when click Insert or update.
     *
     * @param currentCompany the current company
     * @param listBranch the list branch
     * @return ServiceResult
     */
    public ServiceResult<Company_mst> saveCompany(final Company_mst_DTO currentCompany,
        final List<Addresspoint_mst> listBranch) {
        final List<Message> messages = new ArrayList<>();
        ServiceResult<Company_mst> result = null;

        // initial company entity for insert
        if ((currentCompany.getCom_company_code() == null)
            || ArrowStringUtils.EMPTY_STRING.equals(currentCompany.getCom_company_code())) {
            final String companyCode = this.autoNumberring(currentCompany.getAddresspoint_mst().getAdp_code());
            if (StringUtils.isEmpty(companyCode)) {
                // Cannot generate next numbering for company code.
                messages.add(ErrorMessage.comp_003_cannot_generate_company_code());
                return new ServiceResult<>(false, messages);
            }

            final Company_mst newCompany = new Company_mst(StringUtils.idTrim(companyCode));
            BeanCopier.copy(currentCompany, newCompany);

            // Insert new company into database
            this.emMain.persist(newCompany);

            // insert all branch into database
            Basepoint_info basepointInfo;
            if (CollectionUtils.isNotEmpty(listBranch)) {
                for (final Addresspoint_mst branch : listBranch) {
                    basepointInfo = new Basepoint_info(branch, newCompany);
                    newCompany.getBasepoint_infos().add(basepointInfo);
                    this.emMain.persist(basepointInfo);
                }
            }
            messages.add(InfoMessage.comp_001_save_successfully());
            result = new ServiceResult<>(true, newCompany, messages);
        } else {

            // update Company mst
            final Company_mst companyUpdate = Company_mst.find(currentCompany.getCom_company_code());
            BeanCopier.copy(currentCompany, companyUpdate);

            // Edit all branch into database
            Basepoint_info basepointInfo = null;
            final List<Basepoint_info> listCurrentBranchInDb = Instance.get(BasepointInfoService.class)
                .getAllBasepointInfoByCompanyCode(companyUpdate.getCom_company_code());

            // Add new branch into db
            Boolean isExist;
            for (final Addresspoint_mst newBranch : listBranch) {
                isExist = Boolean.FALSE;
                // Check exist branch in database
                for (final Basepoint_info oldBranch : listCurrentBranchInDb) {
                    if ((oldBranch.getBas_point_code() != null)
                        && oldBranch.getBas_point_code().equals(newBranch.getAdp_code())) {

                        // if exist branch quals with head office, set delet_flg = true
                        if (oldBranch.getBas_delete_flg_DIRECT()) {
                            oldBranch.setBas_delete_flg(false);
                        }

                        listCurrentBranchInDb.remove(oldBranch);
                        isExist = Boolean.TRUE;
                        break;
                    }

                }
                // if branch don't exist in database, insert this record into database
                if (!isExist) {
                    basepointInfo = new Basepoint_info(newBranch, companyUpdate);
                    this.emMain.persist(basepointInfo);
                }
            }

            // Delete old branch
            for (final Basepoint_info oldBranch : listCurrentBranchInDb) {
                oldBranch.setBas_delete_flg(true);
            }
            messages.add(InfoMessage.comp_001_save_successfully());
            result = new ServiceResult<>(true, companyUpdate, messages);
        }
        this.emMain.flush();
        return result;
    }

    /**
     * action delete company.
     *
     * @author lehoangngochan
     * @param selectedCompany the selected company
     * @return serviceResult
     */
    public ServiceResult<Company_mst> deleteCompany(final Company_mst selectedCompany) {
        this.repo.findAndRefresh(selectedCompany);
        final List<Message> messages = new ArrayList<>();
        selectedCompany.setCom_delete_flg(true);
        this.repo.saveAndFlush(selectedCompany);
        for (final Basepoint_info branch : selectedCompany.getBasepoint_infos()) {
            branch.setBas_delete_flg(true);
            this.basepointInfoRepository.saveAndFlush(branch);
        }

        messages.add(InfoMessage.comp_001_save_successfully());
        return new ServiceResult<>(true, null, messages);
    }

    /**
     * Creates the dto.
     *
     * @param company the company
     * @return the service result
     */
    public ServiceResult<Company_mst_DTO> createDTO(final Company_mst company) {
        this.repo.findAndRefresh(company);
        return new ServiceResult<>(true, company.getDto());
    }

    /**
     * Auto complete for pulldownlist head office.
     *
     * @author lehoangngochan
     * @param query the query
     * @param listAllAddressPointMst the list all address point mst
     * @return list object exist in pulldownlist
     */
    public List<Addresspoint_mst> autoCompleteHeadOffice(final String query,
        final List<Addresspoint_mst> listAllAddressPointMst) {
        final List<Addresspoint_mst> suggestions = new ArrayList<Addresspoint_mst>();
        for (final Addresspoint_mst add : listAllAddressPointMst) {
            if (add.getAdp_name().startsWith(query)) {
                suggestions.add(add);
            }
        }
        return suggestions;
    }

    /**
     * Auto complete for pulldownlist type of work.
     *
     * @author lehoangngochan
     * @param query the query
     * @param listAllIndustryBigMst the list all industry big mst
     * @return list object exist in pulldownlist
     */
    public List<Industry_big_mst> autoCompleteTypeOfWork(final String query,
        final List<Industry_big_mst> listAllIndustryBigMst) {
        final List<Industry_big_mst> suggestions = new ArrayList<Industry_big_mst>();
        for (final Industry_big_mst add : listAllIndustryBigMst) {
            if (add.getBig_name().startsWith(query)) {
                suggestions.add(add);
            }
        }
        return suggestions;
    }

    /**
     * Extract number.
     *
     * @param companyCode the company code
     * @return the int
     * @throws NumberFormatException the number format exception
     */
    public int extractNumber(final String companyCode) throws NumberFormatException {
        return Integer.parseInt(companyCode.substring(CompanyManagementService.START_INDEX_OF_NUMBERING));
    }

    /**
     * get Auto Number for company code.
     *
     * @author lehoangngochan
     * @param empAdpcode code headoffice
     * @return company code
     */
    public String autoNumberring(final String empAdpcode) {
        final List<Company_mst> listCompaniesHasSameAddress = this.getCompanyCodeForAutoNumberring(empAdpcode);
        if (CollectionUtils.isNotEmpty(listCompaniesHasSameAddress)) {
            final String maxCompanyCode = listCompaniesHasSameAddress.get(0).getCom_company_code();
            try {
                final int nextNumber = this.extractNumber(maxCompanyCode) + 1;
                return new StringBuilder().append(empAdpcode)
                    .append(String.format(CompanyManagementService.NUMBERING_FORMAT, nextNumber)).toString();
            } catch (final NumberFormatException nfe) {
                return null;
            }
        } else {
            return new StringBuffer().append(empAdpcode)
                .append(String.format(CompanyManagementService.NUMBERING_FORMAT, 1)).toString();
        }
    }

    /**
     * get SelectItem for Headoffice.
     *
     * @author lehoangngochan
     * @return list select item
     */
    public List<SelectItem> getSelectItemHeadOffice() {
        final List<SelectItem> companyHeadOffice = new ArrayList<>();
        ServiceResult<List<Addresspoint_mst>> allAddressResult = this.addresspointService.getAllAddressPoints();
        if (allAddressResult.isSuccessful()) {
            final List<Addresspoint_mst> listAddresspointMst = allAddressResult.getData();
            for (final Addresspoint_mst add : listAddresspointMst) {
                companyHeadOffice.add(new SelectItem(add.getAdp_code(), add.getAdp_name_DIRECT()));
            }
        }
        return companyHeadOffice;
    }

    /**
     * Get selectItem for IndustryBigMst.
     *
     * @author lehoangngochan
     * @return list select item
     */
    public List<SelectItem> getSelectItemIndustryBigMst() {
        final List<SelectItem> industryBigMst = new ArrayList<>();
        final List<Industry_big_mst> listIndustryBig = this.industryService.getAllIndustryBig();
        for (final Industry_big_mst obj : listIndustryBig) {
            industryBigMst.add(new SelectItem(obj.getBig_code(), obj.getBig_name()));
        }
        return industryBigMst;
    }

    /**
     * get all name branch.
     *
     * @author lehoangngochan
     * @param companyCode String
     * @param listBasepointInfo the list basepoint info
     * @param listAllAddress the list all address
     * @return String all branch
     */
    public String getBranchByCompanycode(final String companyCode, final List<Basepoint_info> listBasepointInfo,
        final List<Addresspoint_mst> listAllAddress) {
        final StringBuilder sb = new StringBuilder();

        // get Branch of company
        final List<Basepoint_info> listAllBranchByCompanyCode = new ArrayList<Basepoint_info>();
        for (final Basepoint_info base : listBasepointInfo) {
            if (base.getBas_company_code().equals(companyCode)) {
                listAllBranchByCompanyCode.add(base);
            }
        }

        //
        Addresspoint_mst address = null;
        Basepoint_info branch = null;
        for (int i = 0; i < listAllBranchByCompanyCode.size(); i++) {
            branch = listAllBranchByCompanyCode.get(i);
            address = new Addresspoint_mst();

            // get address
            for (final Addresspoint_mst addresspoint : listAllAddress) {
                if (addresspoint.getAdp_code().equals(branch.getBas_point_code())) {
                    address = addresspoint;
                    break;
                }
            }

            // append string
            sb.append(address.getAdp_name_DIRECT());
            if (i < (listAllBranchByCompanyCode.size() - 1)) {
                sb.append(ArrowStringUtils.COMMA).append(ArrowStringUtils.SPACE);
            }
        }
        return sb.toString();
    }

    /**
     * get all basepointInfo from db.
     *
     * @author lehoangngochan
     * @return list Basepoint info
     */
    public List<Basepoint_info> getAllBasepointInfo() {
        final List<Basepoint_info> listBasepointInfo = this.basepointInfoRepository.findAll();
        for (int i = listBasepointInfo.size() - 1; i >= 0; i--) {
            if (listBasepointInfo.get(i).getBas_delete_flg_DIRECT()) {
                listBasepointInfo.remove(i);
            }
        }
        return listBasepointInfo;
    }

    /**
     * get All address.
     *
     * @author lehoangngochan
     * @return list address
     */
    public List<Addresspoint_mst> getAllAddress() {
        return this.addressRepo.findAll();
    }

    /**
     * get label of companylisted display on screen.
     *
     * @author lehoangngochan
     * @param valueListed valus
     * @return String
     */
    public String getLabelCompanyListed(final String valueListed) {
        String strReturn = ArrowStringUtils.EMPTY_STRING;
        for (final SelectItem item : SelectItemGenerator.getListSelectItem(ListType.COMP_LISTED_CODE)) {
            if (valueListed.equals(item.getValue())) {
                strReturn = item.getLabel();
            }
        }
        return strReturn;
    }

    /**
     * prepare data for update. Get data for branch item.
     *
     * @author lehoangngochan
     * @param companyCode the company code
     * @return DualList branch
     */
    public DualListModel<Addresspoint_mst> getInitBranchItemInUpdateCreen(final String companyCode) {
        final List<Addresspoint_mst> branchSource = this.addressRepo.findAll();
        final List<Addresspoint_mst> branchTarget = new ArrayList<Addresspoint_mst>();
        final List<Basepoint_info> lsBranch =
            Instance.get(BasepointInfoService.class).getBasepointInfoByCompanyCode(companyCode);
        for (int i = branchSource.size() - 1; i >= 0; i--) {
            for (final Basepoint_info branch : lsBranch) {
                if (branchSource.get(i).getAdp_code().equals(branch.getBas_point_code())
                    && !branch.getBas_delete_flg_DIRECT()) {
                    branchTarget.add(branchSource.get(i));
                    branchSource.remove(branchSource.get(i));
                }
            }
        }
        return new DualListModel<Addresspoint_mst>(branchSource, branchTarget);
    }

    /**
     * Filter datatable.
     *
     * @author lehoangngochan
     * @return list companies
     */
    public List<Company_mst> getListCompanies() {
        final ArrowQuery<Company_mst> query = new ArrowQuery<>(super.emMain);
        query.select("e").from("Company_mst e ");
        query.where(" com_delete_flg = false");
        // fixed for Postgres
        query.addFilter("company_name", FilterType.OR_STRING,
            "( upper(e.com_company_name) LIKE ? OR upper(e.com_company_furigana) LIKE ? )");
        query.addSorter("company_name", "e.com_company_name");
        query.addFilter("company_customer_code", "upper(e.com_customer_code) LIKE ?");
        query.addSorter("company_customer_code", "e.com_customer_code");
        query.addFilter("company_url", "upper(e.com_url) LIKE ?");
        query.addSorter("company_url", "e.com_url");
        query.addFilter("company_head_office", "upper(e.addresspoint_mst.adp_code) LIKE ?");
        query.addSorter("company_head_office", "e.addresspoint_mst.adp_code");
        query.addNumberFilter("company_type_of_work", "cast(e.industry_big_mst.big_code as double)");
        query.addSorter("company_type_of_work", "e.industry_big_mst.big_code");
        query.addNumberFilter("company_listed", "cast(e.com_listed_code as double)");
        query.addSorter("company_listed", "e.com_listed_code");

        query.orderBy("com_company_code");
        query.orderBy("com_company_name");

        return query.getResultList();
    }

    /**
     * Checks if is allow delete.
     *
     * @return true, if is allow delete
     */
    public boolean isAllowDelete() {
        return this.userCredential.isAllowDelete();
    }

    /**
     * get List Company equals key.
     *
     * @author lehoangngochan
     * @param key String
     * @param lsCompanyMst list company
     * @return list company
     */
    public List<Company_mst> getCompanyExport(final String key, final List<Company_mst> lsCompanyMst) {
        final List<Company_mst> lsComp = new ArrayList<Company_mst>();
        for (final Company_mst comp : lsCompanyMst) {
            if ((comp.getCom_customer_code_DIRECT() != null)
                && key.equals(comp.getCom_customer_code_DIRECT().substring(0, 1))
                && (CompanyManagementService.MAP_EXPORT_BUSINESS_COMPANY_CODE
                    .get(comp.getCom_customer_code_DIRECT().substring(0, 1)) != null)) {
                lsComp.add(comp);
            }
        }
        this.sortListCompanyMst(lsComp);
        return this.prepareDateExport(lsComp);
    }

    /**
     * prepare data for export.
     *
     * @author lehoangngochan
     * @param lsComp input
     * @return list company output
     */
    protected List<Company_mst> prepareDateExport(final List<Company_mst> lsComp) {
        this.sortListCompanyMst(lsComp);

        int first = 1;
        final List<Company_mst> lsCompany = new ArrayList<Company_mst>();
        int customer = 0;
        String key;
        for (final Company_mst comp : lsComp) {
            key = comp.getCom_customer_code_DIRECT().substring(0, 1);
            customer = Integer.parseInt(comp.getCom_customer_code_DIRECT().substring(1, 4));

            if (customer > (first)) {
                for (int i = first; i < customer; i++) {
                    first++;
                    final Company_mst com = new Company_mst();
                    com.setCom_customer_code(this.autoIncrease(key, i));
                    lsCompany.add(com);
                }
            }
            lsCompany.add(comp);
            first++;
        }
        return lsCompany;
    }

    /**
     * get autoIncrease customer code.
     *
     * @author lehoangngochan
     * @param key the key
     * @param number the number
     * @return customer code
     */
    protected String autoIncrease(final String key, final int number) {
        final StringBuilder strReturn = new StringBuilder();
        strReturn.append(key);
        final int lengthOfNumber = String.valueOf(number).length();
        if (lengthOfNumber == 1) {
            strReturn.append(CompanyManagementService.TWO_ZERO);
        } else if (lengthOfNumber == CompanyManagementService.NUMBER_TWO) {
            strReturn.append(CompanyManagementService.ONE_ZERO);
        }
        strReturn.append(number);
        return strReturn.toString();
    }

    /**
     * sort list customer code.
     *
     * @author lehoangngochan
     * @param listCompanies the list companies
     */
    protected void sortListCompanyMst(final List<Company_mst> listCompanies) {
        if (listCompanies.size() > 0) {
            Collections.sort(listCompanies, new Comparator<Company_mst>() {
                @Override
                public int compare(final Company_mst object1, final Company_mst object2) {
                    int intReturn = 0;
                    if ((object1.getCom_customer_code_DIRECT() != null)
                        && (object2.getCom_customer_code_DIRECT() != null)) {
                        intReturn =
                            object1.getCom_customer_code_DIRECT().compareTo(object2.getCom_customer_code_DIRECT());
                    }
                    return intReturn;
                }
            });
        }
    }

    /**
     * get total record.
     *
     * @author lehoangngochan
     * @param totalRecord size of list
     * @return total record
     */
    protected Integer rowNumber(final Integer totalRecord) {
        Integer returnNumber;
        if ((totalRecord % CompanyManagementService.NUMBER_ROWS_OF_BLOCK) > 0) {
            returnNumber = (totalRecord / CompanyManagementService.NUMBER_ROWS_OF_BLOCK) + 1;
        } else {
            returnNumber = (totalRecord / CompanyManagementService.NUMBER_ROWS_OF_BLOCK);
        }
        return returnNumber * CompanyManagementService.NUMBER_ROWS_OF_BLOCK;
    }

    /**
     * Builds the cell style.
     *
     * @param myStyle the my_style
     * @return the HSSF cell style
     */
    private HSSFCellStyle buildCellStyle(HSSFCellStyle myStyle) {
        /* First, let us draw a thin border */
        myStyle = ExcelUtils.setFullBorderStyle(myStyle);

        myStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        return myStyle;
    }

    /**
     * get Export excel.
     *
     * @return file name
     * @throws URISyntaxException the URI syntax exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public String doExport() throws URISyntaxException, IOException {
        String fileExport = null;
        FileOutputStream outFile = null;
        try {
            fileExport = CompanyManagementService.FILE_NAME;
            final InputStream file = CompanyManagementService.class.getClassLoader()
                .getResourceAsStream(CompanyManagementService.TEMPLATE_DIR + "/" + CompanyManagementService.FILE_NAME);
            final HSSFWorkbook workbook = new HSSFWorkbook(file);
            final HSSFSheet sheet = workbook.getSheetAt(0);

            // sort data in list

            final List<Company_mst> listCompanies = this.getCompanyForExportExcel();
            List<Company_mst> listData = null;
            Company_mst comp = null;
            Cell cell = null;
            Row row = null;
            Integer indexCategory = null;
            int totalRow = CompanyManagementService.START_ROW_INDEX;
            for (final String key : CompanyManagementService.LIST_KEY_EXPORT_BUSSINESS_COMPANY_CODE) {
                /* Get access to HSSFCellStyle */
                final HSSFCellStyle myStyle = this.buildCellStyle(workbook.createCellStyle());

                // set background color
                myStyle.setFillForegroundColor(CompanyManagementService.MAP_COLOR_BACKGROUND.get(key));
                indexCategory = CompanyManagementService.ROW_CATEGORY;

                // get list Company by key
                listData = this.getCompanyExport(key, listCompanies);

                // size
                final Integer size = this.rowNumber(listData.size());

                // write company into excel
                for (int i = 0; i < size; i++) {
                    if (i <= (listData.size() - 1)) {
                        comp = listData.get(i);
                    } else {
                        comp = new Company_mst();
                        comp.setCom_customer_code(this.autoIncrease(key, i + 1));
                    }
                    cell = null;
                    // Create a new row in current sheet
                    row = sheet.createRow(totalRow);
                    // Create a new cell in current row
                    cell = row.createCell(CompanyManagementService.COLUMN_CATEGORY);
                    cell.setCellStyle(myStyle);

                    if (i == (indexCategory - 1)) {
                        indexCategory += CompanyManagementService.ROW_CATEGORY_NEXT;
                        cell.setCellValue(CompanyManagementService.MAP_EXPORT_BUSINESS_COMPANY_CODE.get(key));
                    }
                    cell = row.createCell(CompanyManagementService.COLUMN_CUSTOMER_CODE);
                    cell.setCellStyle(myStyle);
                    cell.setCellValue(comp.getCom_customer_code_DIRECT());

                    cell = row.createCell(CompanyManagementService.COLUMN_COMPANY_NAME);
                    cell.setCellStyle(myStyle);
                    cell.setCellValue(comp.getCom_company_name_DIRECT());

                    // set border for cell
                    this.borderRow(row, myStyle);
                    totalRow++;
                }
            }

            // write file
            file.close();
            outFile = new FileOutputStream(new File(fileExport));

            workbook.write(outFile);
        } catch (final IOException e) {
            super.log.debug("EXCEPTION WHEN EXPORT EXCEL IN COMPANYMANAGEMENT MAY BE FILE OUTPUT STREAM", e);
        } finally {
            if (outFile != null) {
                outFile.close();
            }
        }
        return fileExport;
    }

    /**
     * Border row.
     *
     * @param row the row
     * @param myStyle the my style
     */
    private void borderRow(final Row row, final HSSFCellStyle myStyle) {
        Cell cell;
        for (int j = CompanyManagementService.COL_START_BORDER; j <= CompanyManagementService.COL_END_BORDER; j++) {
            cell = row.createCell(j);
            cell.setCellStyle(myStyle);
        }
    }

    /**
     * Will be used as a cache storage.
     *
     * @return the all addresspoints
     */
    public List<Addresspoint_mst> getAllAddresspoints() {
        if (this.allAddresspoints == null) {
            ServiceResult<List<Addresspoint_mst>> allAddressResult = this.addresspointService.getAllAddressPoints();
            if (allAddressResult.isSuccessful()) {
                this.allAddresspoints = allAddressResult.getData();
            }
        }
        return this.allAddresspoints;
    }

    /**
     * Sets the all addresspoints.
     *
     * @param allAddresspoints the new all addresspoints
     */
    public void setAllAddresspoints(final List<Addresspoint_mst> allAddresspoints) {
        this.allAddresspoints = allAddresspoints;
    }

    /**
     * Add Empty item at top to allow user clear the contidion.
     *
     * @param inputQuery the input query
     * @return the list
     */
    public List<Addresspoint_mst> filterHeadOfficeForSearchCompany(final String inputQuery) {
        java.util.function.Predicate<Addresspoint_mst> filterConditions =
            headOffice -> StringUtils.isEmpty(inputQuery) || headOffice.getAdp_name().contains(inputQuery)
                || String.valueOf(headOffice.getAdp_code()).contains(inputQuery);
        List<Addresspoint_mst> result =
            this.getAllAddresspoints().stream().filter(filterConditions).collect(Collectors.toList());

        result.add(0, Addresspoint_mst.EMPTY_ENTITY());
        return result;
    }

    /**
     * Find duplicate customer code.
     *
     * @param companyCode the company code
     * @param customerCode the customer code
     * @return the service result
     */
    public ServiceResult<Company_mst> findDuplicateCustomerCode(final String companyCode, final String customerCode) {
        // If user not input customer code, needn't check duplicate
        if (StringUtils.isEmpty(customerCode)) {
            return new ServiceResult<Company_mst>(false, null, new ArrayList<Message>());
        }

        String query = "SELECT c FROM Company_mst c "
            + "WHERE c.com_company_code <> :companyCode AND c.com_customer_code=:customerCode "
            + "AND c.com_delete_flg = false";
        List<Company_mst> listDuplicated = super.emMain.createQuery(query, Company_mst.class)
            .setParameter("companyCode", companyCode).setParameter("customerCode", customerCode).getResultList();

        if (CollectionUtils.isNotEmpty(listDuplicated)) {
            return new ServiceResult<Company_mst>(true, listDuplicated.get(0));
        }
        return new ServiceResult<Company_mst>(false, null, new ArrayList<Message>());
    }

    public Customer_info_view getCustomerInfo(final String dai_company_code, final String dai_comppoint_code,
        final String dai_compemp_name, final String dai_employee_post) {
        return this.customerInfoRepo.getCustomerInfoByCompanyCodeAndBranchpointCodeAndPsnAndEmpPost(dai_company_code,
            dai_comppoint_code, dai_compemp_name, dai_employee_post);
    }

    /**
     * Find company by company code.
     *
     * @param companyCode the company code
     * @return the company_mst
     */
    public Company_mst findCompanyByCompanyCode(final String companyCode) {
        return this.repo.findActiveCompanyByCompanyCode(companyCode).getAnyResult();
    }
}
