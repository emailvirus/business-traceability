package arrow.framework.faces.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Collections;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.poi.ss.usermodel.Workbook;
import org.omnifaces.util.Faces;
import org.primefaces.util.Constants;


/**
 * The Class FaceletUtils.
 */
public class FaceletUtils {

    /**
     * Checks if is resource available.
     *
     * @param resourcePath the resource path
     * @return true, if is resource available
     */
    public static boolean isResourceAvailable(final String resourcePath) {
        try {
            final boolean value =
                FacesContext.getCurrentInstance().getExternalContext().getResource(resourcePath) != null;
            return value;
        } catch (final Exception e) {
            return false;
        }
    }

    /**
     * Send file to client.
     *
     * @param fileOutputName the file output name
     * @param fileExport the file export
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void sendFileToClient(final String fileOutputName, final Workbook fileExport) throws IOException {
        final OutputStream output = FaceletUtils.getOutputStreamFromHttpServlet(fileOutputName);
        fileExport.write(output);
        output.flush();
        output.close();

        // force to complete response phase
        FacesContext.getCurrentInstance().responseComplete();
    }

    /**
     * Gets the output stream from http servlet.
     *
     * @param fileOutputName the file output name
     * @return the output stream from http servlet
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static OutputStream getOutputStreamFromHttpServlet(final String fileOutputName) throws IOException {
        final FacesContext fc = FacesContext.getCurrentInstance();
        final ExternalContext ec = fc.getExternalContext();
        ec.setResponseContentType("application/ms-excel; charset=UTF-8");
        ec.setResponseCharacterEncoding("UTF-8");
        ec.setResponseHeader("Content-Disposition",
            "attachment; filename*=UTF-8''" + URLEncoder.encode(fileOutputName, "UTF-8"));
        ec.addResponseCookie(Constants.DOWNLOAD_COOKIE, "true", Collections.<String, Object>emptyMap());
        return ec.getResponseOutputStream();
    }

    /**
     * Close stream and force complete response.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void closeStreamAndForceCompleteResponse() throws IOException {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ex = Faces.getExternalContext();
        ex.getResponseOutputStream().flush();
        ex.getResponseOutputStream().close();
        fc.responseComplete();
    }

    /**
     * Send file to client.
     *
     * @param fileOutputName the file output name
     * @param inputStream the file export
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void sendFileToClient(final String fileOutputName, final InputStream inputStream) throws IOException {
        final OutputStream output = FaceletUtils.getOutputStreamFromHttpServlet(fileOutputName);
        int read = 0;
        final byte[] bytes = new byte[1024];
        while ((read = inputStream.read(bytes)) != -1) {
            output.write(bytes, 0, read);
        }
        output.flush();
        output.close();

        // force to complete response phase
        FacesContext.getCurrentInstance().responseComplete();
    }

    /**
     * Gets the output stream from http servlet.
     *
     * @param fileOutputName the file output name
     * @return the output stream from http servlet
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static OutputStream getOutputCsvStreamFromHttpServlet(final String fileOutputName) throws IOException {
        final FacesContext fc = FacesContext.getCurrentInstance();
        final ExternalContext ec = fc.getExternalContext();
        ec.setResponseContentType("text/csv; charset=UTF-8");
        ec.setResponseCharacterEncoding("UTF-8");
        ec.setResponseHeader("Content-Disposition",
            "attachment; filename*=UTF-8''" + URLEncoder.encode(fileOutputName, "UTF-8"));
        ec.addResponseCookie(Constants.DOWNLOAD_COOKIE, "true", Collections.<String, Object>emptyMap());
        return ec.getResponseOutputStream();
    }
}
