package arrow.businesstraceability.constant;

/**
 * The Class BranchPositionConstant.
 */
public class ElasticSearchConstants {


    /**
     * The class Operation of Monthly Report Screen.
     */

    public static final String CHARACTER_SEARCH_LIKE = "*";

    public static class ElasticSearchFilePath {
        public static final String MAPPING_PATH = "/elasticsearch_mapping";

        public static final String SETTING_PATH = "/elasticsearch_setting";

        public static final String COMPANY_SUGGEST_MAPPING = "/company_suggest.txt";

        public static final String COMPANY_MST_MAPPING = "/company_mst.txt";

        public static final String COMPANY_MST_SETTING = "/company_mst_setting.txt";

        public static final String SAN_DATA_SETTING = "/san_card_data_setting.txt";

        public static final String SAN_DATA_MAPPING = "/san_card_data_mapping.txt";
    }

    public static class CustomerInfoView {

        /** The Constant INDEX. */
        public static final String INDEX_VALUE = "customer_infos";

        /** The Constant TYPE. */
        public static final String TYPE_VALUE = "customer_infos";

        public static final String COM_COMPANY_CODE = "com_company_code";

        public static final String BRANCH_POINT_CODE = "branch_point_code";

        public static final String DAI_COMEMP_NAME = "dai_compemp_name";

        public static final String DAI_COMEMP_POST = "dai_employee_post";

        public static final int SIZE = 9999;

    }

    public static class WildCardSearchWord {

        public static final String MATCHES_ANY_SEQUENCE = "*";

        public static final String MATCHES_ANY_SINGLE_CHARACTER = "?";

    }
    public static class Company_mst_suggest {

        /** The Constant INDEX. */
        public static final String INDEX_VALUE = "company_mst_suggests";

        /** The Constant TYPE. */
        public static final String TYPE_VALUE = "company_mst_suggest";

        public static final String SUGGEST_FIELD = "company_suggest";

        public static final int MAX_SIZE_SUGGEST = 20;

    }
    public static class Company_mst {
        public static final String INDEX_VALUE = "company_msts";

        public static final String TYPE_VALUE = "company_mst";

        public static final String COM_COMPANY_NAME = "com_company_name";

        public static final String COM_COMPANY_FURIGANA = "com_company_furigana";

        public static final String COM_CUSTOMER_CODE = "com_customer_code";

        public static final String COM_URL = "com_url";

        public static final String COM_COMPANY_CODE = "com_company_code";

        public static final String WORK_TYPE = "worktype";

        public static final String LISTED = "listed";
    }

    public static class Boost {
        public static final Float HALFPOINT = 0.5f;

        public static final Float ONEFIFTH = 0.2f;

        public static final Float ONEOVERTEN = 0.1f;

        public static final Float SIXTENTH = 0.6f;
    }
    public static class ElasticsearchProperties {
        public static final String HOST_VALUE = "127.0.0.1";

        public static final int PORT_VALUE = 9300;

        public static final String INDEX_KEYWORD = "index";

        public static final String TYPE_KEYWORD = "type";

        public static final String INPUT_KEYWORD = "input";

        public static final String OUTPUT_KEYWORD = "output";

        public static final String PAYLOAD_KEYWORD = "payload";

    }

    public static class San_com_info {
        public static final String INDEX_VALUE = "san_com_info";

        public static final String TYPE_VALUE = "san_com_info";

        public static final String FIELD_NAME_COMPANY = "name_company";

        public static final String FIELD_ID_COMPANY_INT = "id_company";
    }

    public static class San_card_data {
        public static final String INDEX_VALUE = "san_card_data";

        public static final String TYPE_VALUE = "san_card_data";

        public static final String FIELD_ID_COMPANY = "id_san_company";

        public static final String FIELD_ID_CARD = "id_san_card";

        public static final String FIELD_ID_COMPANY_INT = "id_company";

        public static final String FIELD_NAME_COMPANY = "name_company";

        public static final String FIELD_ENUM_INFO_VALIDATION = "enum_info_validation";

        public static final String ENUM_INFO_VALIDATION_STATUS_DELETE = "D";

        public static final int MAX_SIZE = 2147483646;

        public static final String STATUS_INFO_VALIDATION_PROCESS = "enum_info_validation";

        public static final String DELETE = "d";

        public static final String TS_LAST_UPDATED = "ts_last_updated";

        public static final String FIELD_ID_SAN_CARD = "id_san_card";

        public static final String FIELD_ID_SAN_COMPANY = "id_san_company";
    }

    public static class San_com_branch {
        public static final String INDEX_VALUE = "san_com_branch";

        public static final String TYPE_VALUE = "san_com_branch";

        public static final String ID_COMPANY = "id_company";

        public static final String NAME_BRANCH = "name_branch";
    }
    public static class San_com_whole_card {
        public static final String INDEX_VALUE = "san_com_whole_card";

        public static final String TYPE_VALUE = "san_com_whole_card";

        public static final String ID_COMPANY = "id_company";
    }
    public static class San_com_live_card {
        public static final String INDEX_VALUE = "san_com_live_card";

        public static final String TYPE_VALUE = "san_com_live_card";

        public static final String ID_COMPANY = "id_company";
    }
    public static class San_com_live_owner {
        public static final String INDEX_VALUE = "san_com_live_owner";

        public static final String TYPE_VALUE = "san_com_live_owner";

        public static final String ID_COMPANY = "id_company";
    }


    public static class San_com_site {
        public static final String INDEX_VALUE = "san_com_site";

        public static final String TYPE_VALUE = "san_com_site";

        public static final String ID_COMPANY = "id_company";

        public static final String CODE_ZIP = "code_zip";

        public static final String ADDR_ALL = "addr_all";

        public static final String ADDR_PREF = "addr_pref";

        public static final String ADDR_CITY = "addr_city";

        public static final String ADDR_BLOCK = "addr_block";

        public static final String ADDR_BLDG = "addr_bldg";
    }

    public static class San_com_cnumber {
        public static final String INDEX_VALUE = "san_com_cnumber";

        public static final String TYPE_VALUE = "san_com_cnumber";

        public static final String ID_COMPANY = "id_company";

        public static final String TEL11 = "tel11";

        public static final String FAX = "fax";
    }

    public static class San_com_mdomain {
        public static final String INDEX_VALUE = "san_com_mdomain";

        public static final String TYPE_VALUE = "san_com_mdomain";

        public static final String ID_COMPANY = "id_company";

        public static final String EMAIL_DOMAIN = "email_domain";

    }

    public static class San_com_udomain {
        public static final String INDEX_VALUE = "san_com_udomain";

        public static final String TYPE_VALUE = "san_com_udomain";

        public static final String ID_COMPANY = "id_company";

        public static final String URL_DOMAIN = "url_domain";

    }

    public static class San_com_live_stat {
        public static final String INDEX_VALUE = "san_com_live_stat";

        public static final String FIELD_ID_COMPANY = "id_company";

    }

    public static class San_com_live_client {
        public static final String INDEX_VALUE = "san_com_live_client";

    }

    public static class San_card_tag {
        public static final String INDEX_VALUE = "san_card_tag";

    }
    public static class San_idmap_card {
        public static final String INDEX_VALUE = "san_idmap_card";

    }

    public static class San_idmap_company {
        public static final String INDEX_VALUE = "san_idmap_company";

    }

    public static class San_idmap_person {
        public static final String INDEX_VALUE = "san_idmap_person";

    }

}
