package arrow.businesstraceability.util.upload;


public class UploadHelper {

    private static final String DIR_FORMAT_WITH_TWO_LEVELS_FOLDER = "%s/%s";

    private static final String DIR_FORMAT_WITH_THREE_LEVELS_FOLDER = "%s/%s/%s";


    // $UPLOAD_DIR/<credit_id>/<entity_id>/<filename>
    public static String getUploadPath(final String uploadDir, final int credit_id, final int entity_id) {
        return String.format(UploadHelper.DIR_FORMAT_WITH_THREE_LEVELS_FOLDER, uploadDir, credit_id, entity_id);
    }

}
