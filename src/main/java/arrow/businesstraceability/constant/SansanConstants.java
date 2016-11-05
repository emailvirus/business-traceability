package arrow.businesstraceability.constant;

import java.util.Arrays;
import java.util.List;

/**
 * The Class SansanConstants.
 */
public class SansanConstants {

    /** Default number of import by file. */
    public static final int NEW_NUMBER_IMPORT_FILE = 1;

    /** Default number of import by API. */
    public static final int NEW_NUMBER_IMPORT_API_FILE = 0;

    /** Default number of count update company. */
    public static final int NEW_NUMBER_UPDATE_ID_COMPANY = 0;

    /** The Constant NEW_NUMBER_UPDATE_CARDINFO. */
    public static final int NEW_NUMBER_UPDATE_CARDINFO = 0;

    public static final int INCREASE_COUNT_ONE = 1;

    public static final String FIELD_EMAIL_NAME = "email_name";

    public static final String FIELD_EMAIL_DOMAIN = "email_domain";

    public static final String FIELD_URL_DOMAIN = "url_domain";

    public static final String FIELD_EMAIL_NAME2 = "email_name2";

    public static final String FIELD_EMAIL_DOMAIN2 = "email_domain2";

    public static final String FIELD_URL_DOMAIN2 = "url_domain2";

    public static final String FIELD_ENUM_INFO_VALIDATION = "enum_info_validation";

    public static final String FIELD_ENUM_IMPORT_PROC = "enum_import_proc";

    public static final String FIELD_TS_CREATE = "ts_create";

    public static final String FIELD_TS_COM_CREATION = "ts_com_creation";

    public static final String FIELD_CN_FILEIMPORT = "cn_fileimport";

    public static final String FIELD_CN_APIIMPORT = "cn_apiimport";

    public static final String FIELD_CN_COMID_UPDATE = "cn_comid_update";

    public static final String FIELD_CN_CARDINFO_UPDATE = "cn_cardinfo_update";

    public static final String FIELD_TS_LAST_UPDATED = "ts_last_updated";

    public static final String FIELD_TAGS = "tags";

    public static final String SYMBOL_SPILIT_TAG = ",";

    public static final String SPECIAL_CHARACTER_UIID = "-";

    public static final String FIELD_ID_CARD = "id_card";

    public static final String FIELD_ID_COMPANY = "id_company";

    public static final String FIELD_ID_PERSON = "id_person";

    public static final String FIELD_ID_SAN_CARD = "id_san_card";

    public static final String FIELD_ID_SAN_COMPANY = "id_san_company";

    public static final String FIELD_ID_SAN_PERSON = "id_san_person";

    /**
     * The Class enum_info_validation.
     */
    public static class Enum_info_validation {

        public static final char DELETE = 'D';

        public static final char NEW = 'N';

        public static final char OTHER = 'O';

        public static final char UNCHANGED_OR_CHANGE_ITEM_REFLECTED = 'U';

        public static final char EXISTED_AND_OTHER_ATTRIBUTES_CHANGED = 'E';

        public static final char EXISTED_AND_COMPANY_ID_CHANGED = 'C';
    }

    /**
     * The Class enum_import_proc.
     */
    public static class Enum_import_proc {
        public static final char SYNC_FILE = 'F';

        public static final char SYNC_API = 'A';

        public static final char SYNC_API_AND_FILE = 'S';

        public static final char OTHER = 'O';

    }

    /**
     * Input rule of Sansan namecard.
     *
     * @author HongLM - ArrowTechnology, Ltd.
     * @version Apr 12, 2016
     */
    public static class NameCardInfoInputRules {

        public static final int NAME_COMPANY_LENGTH = 250;

        public static final int NAME_COM_ENG_LENGTH = 250;

        public static final int NAME_COM_KANA_LENGTH = 250;

        public static final int NAME_BRANCH_LENGTH = 250;

        public static final int POSITION_LENGTH = 250;

        public static final int NAME_CL_FIRST_LENGTH = 250;

        public static final int NAME_CL_LAST_LENGTH = 250;

        public static final int NAME_CL_FIRST_KANA_LENGTH = 250;

        public static final int NAME_CL_LAST_KANA_LENGTH = 250;

        public static final int CODE_ZIP_LENGTH = 12;

        public static final int ADDR_ALL_LENGTH = 250;

        public static final int TEL11_LENGTH = 30;

        public static final int TEL12_LENGTH = 30;

        public static final int FAX_LENGTH = 30;

        public static final int MPHONE_LENGTH = 30;

        public static final int EMAIL_LENGTH = 250;

        public static final int URL_LENGTH = 250;

        public static final int ZIPCODE2_LENGTH = 12;

        public static final int ADDR_ALL2_LENGTH = 1000;

        public static final int TEL21_LENGTH = 30;

        public static final int TEL22_LENGTH = 30;

        public static final int FAX2_LENGTH = 30;

        public static final int MPHONE2_LENGTH = 30;

        public static final int EMAIL2_LENGTH = 250;

        public static final int URL2_LENGTH = 250;
    }

    /**
     * CSV file constants.
     *
     * @author HaiND ArrowTechnology, Ltd.
     * @version Apr 13, 2016
     */
    public static class CsvFileConstants {

        public static final String FILE_PATH = "/csv/lastest_data.csv";

        public static final String SEPARATOR = ",";
    }


    /**
     * The Class UpdateFlag.
     */
    public static class UpdateFlag {
        public static final String DELETE = "delete";

        public static final String UNCHANGE = "unchange";

        public static final String OTHER_CHANGE = "otherChange";

        public static final String ID_COMPANY_CHANGE = "idCompanyChange";

    }

    public static class BooleanConstant {
        public static final String YES = "Y";

        public static final String NO = "N";

    }

    public static class Api {
        public static final String HAS_DATA = "hasData";

        public static final String PARAM_REGISTERED_FROM = "registeredFrom";

        public static final String PARAM_REGISTERED_TO = "registeredTo";

        public static final String PARAM_OFFSET = "offset";

        public static final String RESPONSE_DATA = "data";

        public static final String RESPONSE_HAS_MORE = "hasMore";

        public static final String PARAM_RANGE = "range";

        public static final String PARAM_ENTRY_STATUS = "entryStatus";

        public static final String PARAM_LIMIT = "limit";
    }

    public static class TableName {
        public static final String SAN_CARD_DATA = "san_card_data";

        public static final String SAN_CARD_TAG = "san_card_tag";

        public static final String SAN_COM_BRANCH = "san_com_branch";

        public static final String SAN_COM_CNUMBER = "san_com_cnumber";

        public static final String SAN_COM_INFO = "san_com_info";

        public static final String SAN_COM_LIVE_CLIENT = "san_com_live_client";

        public static final String SAN_COM_LIVE_OWNER = "san_com_live_owner";

        public static final String SAN_COM_LIVE_STAT = "san_com_live_stat";

        public static final String SAN_COM_SITE = "san_com_site";

        public static final String SAN_COM_UDOMAIN = "san_com_udomain";

        public static final String SAN_COM_MDOMAIN = "san_com_mdomain";

        public static final String SAN_COM_WHOLE_CARD = "san_com_whole_card";

        public static final String SAN_IDMAP_CARD = "san_idmap_card";

        public static final String SAN_IDMAP_COMPANY = "san_idmap_company";

        public static final String SAN_IDMAP_PERSON = "san_idmap_person";
    }

    public static List<String> getListDefaultExcludeFields() {
        return Arrays.asList("pk", "isPersisted", "selected", "object_version", "version", "last_updated_at");
    }
}
