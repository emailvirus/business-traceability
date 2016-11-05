package arrow.businesstraceability.stream.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamGobbler extends Thread {
    private final InputStream is;

    public StreamGobbler(final InputStream is) {
        this.is = is;
    }

    @Override
    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(this.is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                // Do nothing
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
