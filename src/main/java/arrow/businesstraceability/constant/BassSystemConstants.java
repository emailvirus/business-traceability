package arrow.businesstraceability.constant;

import javax.inject.Named;

/**
 * The constant of BASS system.
 */
@Named
public class BassSystemConstants {

    /**
     * List table export to BASS system.
     *
     * @author ArrowTechnology, Ltd.
     * @version May 12, 2016 1.0
     */
    public static class BassExportTable {

        /** Table ACC_COM_ENTITY. */
        public static final String ACC_COM_ENTITY = "ACC_COM_ENTITY";

        /** Table ACC_COM_CREDIT. */
        public static final String ACC_COM_CREDIT = "ACC_COM_CREDIT";

        /** Table SAN_COM_INFO. */
        public static final String SAN_COM_INFO = "SAN_COM_INFO";

        /** Table SAN_COM_BRANCH. */
        public static final String SAN_COM_BRANCH = "SAN_COM_BRANCH";

        /** Table SAN_COM_SITE. */
        public static final String SAN_COM_SITE = "SAN_COM_SITE";

        /** Table SAN_COM_CNUMBER. */
        public static final String SAN_COM_CNUMBER = "SAN_COM_CNUMBER";
    }

    /**
     * Export file type to BASS.
     *
     * @author ArrowTechnology, Ltd.
     * @version May 12, 2016 1.0
     */
    public static class ExportFileType {

        /** Table CSV type. */
        public static final String CSV_EXTENSION = ".csv";
    }

    /**
     * File name of created.
     *
     * @author ArrowTechnology, Ltd.
     * @version May 12, 2016 {revision}
     */
    public static class FileNameElement {

        /** Hyphen character . */
        public static final String HYPHEN_CHARACTER = "_";
    }
}
