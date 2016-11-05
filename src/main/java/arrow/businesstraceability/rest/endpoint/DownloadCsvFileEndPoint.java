package arrow.businesstraceability.rest.endpoint;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import arrow.businesstraceability.constant.Constants;

/**
 * The Class DownloadCsvFileEndPoint.
 */
@Path("/bass")
@RequestScoped
public class DownloadCsvFileEndPoint extends AbstractEndpoint {


    /**
     * Gets the file.
     *
     * @param folderPath the folder path
     * @param fileName the file name
     * @return the file
     * @throws UnsupportedEncodingException the unsupported encoding exception
     * @throws FileNotFoundException the file not found exception
     */
    @GET
    @Path("/download/{folder-path}/{file-name}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getFile(@PathParam("folder-path") final String folderPath,
        @PathParam("file-name") final String fileName) throws UnsupportedEncodingException, FileNotFoundException {
        String decodeFolderPath = URLDecoder.decode(folderPath, Constants.DEFAULT_ENCODING);
        File file = new File(decodeFolderPath, URLDecoder.decode(fileName, Constants.DEFAULT_ENCODING));
        InputStream stream = new FileInputStream(file);
        ResponseBuilder buider = Response.ok().entity(stream);
        buider.header("Content-Disposition", "attachment; filename=" + file.getName());
        return buider.build();
    }


}
