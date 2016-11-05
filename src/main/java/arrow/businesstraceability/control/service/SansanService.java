package arrow.businesstraceability.control.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.fasterxml.jackson.core.JsonProcessingException;

import arrow.businesstraceability.base.AbstractService;
import arrow.businesstraceability.constant.SansanConstants;
import arrow.businesstraceability.persistence.entity.San_card_data;
import arrow.businesstraceability.persistence.entity.San_card_tag;
import arrow.businesstraceability.persistence.entity.San_com_branch;
import arrow.businesstraceability.persistence.entity.San_com_cnumber;
import arrow.businesstraceability.persistence.entity.San_com_live_client;
import arrow.businesstraceability.persistence.entity.San_com_live_owner;
import arrow.businesstraceability.persistence.entity.San_com_live_stat;
import arrow.businesstraceability.persistence.entity.San_com_mdomain;
import arrow.businesstraceability.persistence.entity.San_com_site;
import arrow.businesstraceability.persistence.entity.San_com_udomain;
import arrow.businesstraceability.persistence.entity.San_com_whole_card;
import arrow.businesstraceability.persistence.entity.San_idmap_card;
import arrow.businesstraceability.persistence.entity.San_idmap_company;
import arrow.businesstraceability.persistence.entity.San_idmap_person;
import arrow.businesstraceability.persistence.repository.San_card_dataRepository;
import arrow.businesstraceability.persistence.repository.San_card_tagRepository;
import arrow.businesstraceability.persistence.repository.San_com_branchRepository;
import arrow.businesstraceability.persistence.repository.San_com_cnumberRepository;
import arrow.businesstraceability.persistence.repository.San_com_live_clientRepository;
import arrow.businesstraceability.persistence.repository.San_com_live_ownerRepository;
import arrow.businesstraceability.persistence.repository.San_com_live_statRepository;
import arrow.businesstraceability.persistence.repository.San_com_mdomainRepository;
import arrow.businesstraceability.persistence.repository.San_com_siteRepository;
import arrow.businesstraceability.persistence.repository.San_com_udomainRepository;
import arrow.businesstraceability.persistence.repository.San_com_whole_cardRepository;
import arrow.businesstraceability.persistence.repository.San_idmap_cardRepository;
import arrow.businesstraceability.persistence.repository.San_idmap_companyRepository;
import arrow.businesstraceability.persistence.repository.San_idmap_personRepository;
import arrow.framework.util.JsonUtils;
import arrow.framework.util.StringUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class SansanService.
 */
public class SansanService extends AbstractService {

    /** The san card repo. */
    @Inject
    private San_card_dataRepository sanCardRepo;

    /** The id map card repo. */
    @Inject
    private San_idmap_cardRepository idMapCardRepo;

    /** The id map company repo. */
    @Inject
    private San_idmap_companyRepository idMapCompanyRepo;

    /** The id map person repo. */
    @Inject
    private San_idmap_personRepository idMapPersonRepo;

    /** The entity manager. */
    @Inject
    private EntityManager entityManager;

    /** The Constant ENTITY_PACKAGE_PATH. */
    private static final String ENTITY_PACKAGE_PATH = "arrow.businesstraceability.persistence.entity";

    /** The san com u domain repo. */
    @Inject
    private San_com_udomainRepository sanComUDomainRepo;

    /** The san com whole card repo. */
    @Inject
    private San_com_whole_cardRepository sanComWholeCardRepo;

    /** The san com live owner repo. */
    @Inject
    private San_com_live_ownerRepository sanComLiveOwnerRepo;

    /** The san com live stat repo. */
    @Inject
    private San_com_live_statRepository sanComLiveStatRepo;

    /** The san com live client repo. */
    @Inject
    private San_com_live_clientRepository sanComLiveClientRepo;

    /** The san com branch repo. */
    @Inject
    private San_com_branchRepository sanComBranchRepo;

    /** The san com m domain repo. */
    @Inject
    private San_com_mdomainRepository sanComMDomainRepo;

    /** The san com site repo. */
    @Inject
    private San_com_siteRepository sanComSiteRepo;

    /** The san com c number repo. */
    @Inject
    private San_com_cnumberRepository sanComCNumberRepo;

    /** The san card tag repo. */
    @Inject
    private San_card_tagRepository sanCardTagRepo;

    /** The Constant PACKAGE_MIXIN_PATH. */
    private static final String PACKAGE_MIXIN_PATH = "arrow.businesstraceability.restfull.mixin";

    /**
     * Gets the all san card data.
     *
     * @return the all san card data
     */
    public List<San_card_data> getAllSanCardData() {
        return this.sanCardRepo.findAll();
    }

    /**
     * Gets the all san id map card.
     *
     * @return the all san id map card
     */
    public List<San_idmap_card> getAllSanIdMapCard() {
        return this.idMapCardRepo.findAll();
    }

    /**
     * Gets the all san id map company.
     *
     * @return the all san id map company
     */
    public List<San_idmap_company> getAllSanIdMapCompany() {
        return this.idMapCompanyRepo.findAll();
    }

    /**
     * Gets the all san id map person.
     *
     * @return the all san id map person
     */
    public List<San_idmap_person> getAllSanIdMapPerson() {
        return this.idMapPersonRepo.findAll();
    }

    /**
     * Gets the data san table.
     *
     * @param tableName the table name
     * @param idSanComp the id san comp
     * @param idSanCardData the id san card data
     * @return the data san table
     * @throws ClassNotFoundException the class not found exception
     * @throws JsonProcessingException the json processing exception
     */
    public String getDataSanTable(final String tableName, String idSanComp, String idSanCardData)
        throws ClassNotFoundException, JsonProcessingException {
        // build query
        idSanCardData = idSanCardData == null ? null : idSanCardData.toUpperCase();
        idSanComp = idSanComp == null ? null : idSanComp.toUpperCase();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT t from ").append(StringUtils.makeUpperCaseFirstCharOnly(tableName)).append(" t ");
        if (tableName.equals(SansanConstants.TableName.SAN_CARD_DATA)
            || tableName.equals(SansanConstants.TableName.SAN_IDMAP_CARD)) {
            queryBuilder.append("WHERE UPPER(t.id_san_card) like '%").append(idSanCardData).append("%'");
        } else if (tableName.equals(SansanConstants.TableName.SAN_CARD_TAG)) {
            queryBuilder.append(",San_idmap_card sc ")
                .append("WHERE sc.id_card = t.id_card AND UPPER(sc.id_san_card) like '%").append(idSanCardData)
                .append("%'");
        } else if (tableName.equals(SansanConstants.TableName.SAN_COM_WHOLE_CARD)) {
            queryBuilder.append(",San_idmap_card sc ,San_idmap_company scom WHERE t.id_card =sc.id_card AND ")
                .append(" t.id_company = scom.id_company AND ").append("UPPER(sc.id_san_card) like '%")
                .append(idSanCardData).append("%' ").append(" AND UPPER(scom.id_san_company) like '%").append(idSanComp)
                .append("%' ");
        } else {
            queryBuilder.append(",San_idmap_company scom WHERE  t.id_company = scom.id_company")
                .append(" AND UPPER(Scom.id_san_company) like '%").append(idSanComp).append("%' ");
        }
        // execute query
        Class<?> clazz =
            SansanService.getClassByName(StringUtils.makeUpperCaseFirstCharOnly(tableName),
                SansanService.ENTITY_PACKAGE_PATH);
        @SuppressWarnings("unchecked")
        List<Object> objects =
            (List<Object>) this.entityManager.createQuery(queryBuilder.toString(), clazz).getResultList();
        if (objects.size() == 0) {
            return null;
        }
        String outputJson = JsonUtils.getJson(objects, clazz);
        return outputJson;
    }

    /**
     * Gets the class by name.
     *
     * @param name the name
     * @param pakage the pakage
     * @return the class by name
     * @throws ClassNotFoundException the class not found exception
     */
    private static Class<?> getClassByName(final String name, final String pakage) throws ClassNotFoundException {
        return Class.forName(pakage + "." + name);
    }

    /**
     * Gets the all san com branch.
     *
     * @return the all san com branch
     */
    public List<San_com_branch> getAllSanComBranch() {
        return this.sanComBranchRepo.findAll();
    }

    /**
     * Find id san company by id company.
     *
     * @param id_company the id_company
     * @return the string
     */
    public String findIdSanCompanyByIdCompany(final int id_company) {
        return this.idMapCompanyRepo.findIdSanCompanyByIdCompany(id_company);
    }

    /**
     * Gets the all san com m domain.
     *
     * @return the all san com m domain
     */
    public List<San_com_mdomain> getAllSanComMDomain() {
        return this.sanComMDomainRepo.findAll();
    }

    /**
     * Gets the all san com u domain.
     *
     * @return the all san com u domain
     */
    public List<San_com_udomain> getAllSanComUDomain() {
        return this.sanComUDomainRepo.findAll();
    }

    /**
     * Gets the all san com whole card.
     *
     * @return the all san com whole card
     */
    public List<San_com_whole_card> getAllSanComWholeCard() {
        return this.sanComWholeCardRepo.findAll();
    }

    /**
     * Find id san card by id card.
     *
     * @param id_card the id_card
     * @return the string
     */
    public String findIdSanCardByIdCard(final int id_card) {
        return this.idMapCardRepo.findIdSanCardByIdCard(id_card);
    }

    /**
     * Gets the all san com live owner.
     *
     * @return the all san com live owner
     */
    public List<San_com_live_owner> getAllSanComLiveOwner() {
        return this.sanComLiveOwnerRepo.findAll();
    }

    /**
     * Gets the all san com live stat.
     *
     * @return the all san com live stat
     */
    public List<San_com_live_stat> getAllSanComLiveStat() {
        return this.sanComLiveStatRepo.findAll();
    }

    /**
     * Gets the all san com live client.
     *
     * @return the all san com live client
     */
    public List<San_com_live_client> getAllSanComLiveClient() {
        return this.sanComLiveClientRepo.findAll();
    }

    /**
     * Find id san person by id person.
     *
     * @param id_person the id_person
     * @return the string
     */
    public String findIdSanPersonByIdPerson(final int id_person) {
        return this.idMapPersonRepo.findIdSanPersonByIdPerson(id_person);
    }

    /**
     * Gets the all san comsite.
     *
     * @return the all san comsite
     */
    public List<San_com_site> getAllSanComsite() {
        return this.sanComSiteRepo.findAll();
    }

    /**
     * Gets the all san com c number.
     *
     * @return the all san com c number
     */
    public List<San_com_cnumber> getAllSanComCNumber() {
        return this.sanComCNumberRepo.findAll();
    }

    /**
     * Gets the fields name of class.
     *
     * @param tableSelected the table selected
     * @return the fields name of class
     * @throws ClassNotFoundException the class not found exception
     */
    public String getFieldsNameOfClass(final String tableSelected) throws ClassNotFoundException {
        String className = SansanService.removeAlias(StringUtils.makeUpperCaseFirstCharOnly(tableSelected)) + "Mixin";
        Class<?> clazz = SansanService.getClassByName(className, SansanService.PACKAGE_MIXIN_PATH);
        Method[] methods = clazz.getDeclaredMethods();
        StringBuilder fieldsName = new StringBuilder();
        fieldsName.append("[");
        int size = methods.length;
        for (int i = 0; i < (size - 1); i++) {
            fieldsName.append("\"");
            fieldsName.append(methods[i].getName().substring(3));
            fieldsName.append("\"");
            fieldsName.append(",");
        }
        fieldsName.append("\"");
        fieldsName.append(methods[size - 1].getName().substring(3));
        fieldsName.append("\"");
        fieldsName.append("]");
        return fieldsName.toString().toLowerCase();
    }

    /**
     * Removes the alias.
     *
     * @param input the input
     * @return the string
     */
    private static String removeAlias(final String input) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) != 95) {
                output.append(input.charAt(i));
                continue;
            }
            output.append(Character.toUpperCase(input.charAt(i + 1)));
            i++;
        }
        return output.toString();

    }

    /**
     * Gets the quantity total record data san table.
     *
     * @param tableName the table name
     * @return the quantity total record data san table
     */
    public long getQuantityTotalRecordDataSanTable(final String tableName) {
        return (long) this.entityManager.createQuery(
            "SELECT count(t) FROM " + StringUtils.makeUpperCaseFirstCharOnly(tableName) + " t").getSingleResult();
    }

    /**
     * Gets the enum info metadata.
     *
     * @return the enum info metadata
     * @throws IllegalArgumentException the illegal argument exception
     * @throws IllegalAccessException the illegal access exception
     */
    public Map<String, Long> getEnumInfoMetadata() throws IllegalArgumentException, IllegalAccessException {
        Map<String, Long> mapEnumInfoMetadata = new HashMap<>();
        Field[] fields = SansanConstants.Enum_info_validation.class.getDeclaredFields();

        for (Field field : fields) {
            char status = field.getChar(null);
            StringBuilder query = new StringBuilder();
            query.append("SELECT count(").append(SansanConstants.FIELD_ENUM_INFO_VALIDATION).append(")")
                .append(" FROM ")
                .append(StringUtils.makeUpperCaseFirstCharOnly(SansanConstants.TableName.SAN_CARD_DATA))
                .append(" WHERE ").append(SansanConstants.FIELD_ENUM_INFO_VALIDATION + "='").append(status + "'");
            long countInDataBase = (long) this.entityManager.createQuery(query.toString()).getSingleResult();
            mapEnumInfoMetadata.put(field.getName(), countInDataBase);
        }

        return mapEnumInfoMetadata;
    }

    /**
     * Gets the enum proc metadata.
     *
     * @return the enum proc metadata
     * @throws IllegalArgumentException the illegal argument exception
     * @throws IllegalAccessException the illegal access exception
     */
    public Map<String, Long> getEnumProcMetadata() throws IllegalArgumentException, IllegalAccessException {
        Map<String, Long> mapEnumProcMetadata = new HashMap<>();
        Field[] fields = SansanConstants.Enum_import_proc.class.getDeclaredFields();

        for (Field field : fields) {
            char status = field.getChar(null);
            StringBuilder query = new StringBuilder();
            query.append("SELECT count(").append(SansanConstants.FIELD_ENUM_IMPORT_PROC).append(")").append(" FROM ")
                .append(StringUtils.makeUpperCaseFirstCharOnly(SansanConstants.TableName.SAN_CARD_DATA))
                .append(" WHERE ").append(SansanConstants.FIELD_ENUM_IMPORT_PROC + "='").append(status + "'");
            long countInDataBase = (long) this.entityManager.createQuery(query.toString()).getSingleResult();
            mapEnumProcMetadata.put(field.getName(), countInDataBase);
        }

        return mapEnumProcMetadata;
    }

    /**
     * Gets the company metadata.
     *
     * @return the company metadata
     */
    public Map<String, Long> getCompanyMetadata() {
        Map<String, Long> mapCompanyMetadataIsTemporary = new HashMap<>();
        StringBuilder tempId = new StringBuilder();
        StringBuilder noTempId = new StringBuilder();
        // create query find temp id company
        tempId.append("SELECT count(c) FROM ")
            .append(StringUtils.makeUpperCaseFirstCharOnly(SansanConstants.TableName.SAN_COM_INFO + " c"))
            .append(" WHERE ").append(SansanConstants.FIELD_ID_SAN_COMPANY).append(" LIKE '#%'");
        long countInDataBase = (long) this.entityManager.createQuery(tempId.toString()).getSingleResult();
        mapCompanyMetadataIsTemporary.put(SansanConstants.BooleanConstant.YES, countInDataBase);
        // create query find id sansan company
        noTempId.append("SELECT count(c) FROM ")
            .append(StringUtils.makeUpperCaseFirstCharOnly(SansanConstants.TableName.SAN_COM_INFO + " c"))
            .append(" WHERE ").append(SansanConstants.FIELD_ID_SAN_COMPANY).append(" NOT LIKE '#%'");
        countInDataBase = (long) this.entityManager.createQuery(noTempId.toString()).getSingleResult();
        mapCompanyMetadataIsTemporary.put(SansanConstants.BooleanConstant.NO, countInDataBase);
        return mapCompanyMetadataIsTemporary;
    }

    /**
     * Gets the all san card tag.
     *
     * @return the all san card tag
     */
    public List<San_card_tag> getAllSanCardTag() {
        return this.sanCardTagRepo.findAll();
    }

}
