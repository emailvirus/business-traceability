package arrow.framework.util;

import java.io.Writer;

import com.opencsv.CSVWriter;

/**
 * A very simple CSV writer released under a commercial-friendly license.
 *
 * @author Glen Smith
 */
public class CustomCsvWriter extends CSVWriter {

    public CustomCsvWriter(final Writer writer, final char separator, final char quotechar, final char escapechar) {
        super(writer, separator, quotechar, escapechar);
    }

    @Override
    public void writeNext(final String[] nextLine) {
        this.writeNext(nextLine, false);
    }
}
