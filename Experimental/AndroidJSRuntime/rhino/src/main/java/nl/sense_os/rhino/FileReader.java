package nl.sense_os.rhino;

import java.io.IOException;
import java.io.InputStream;

/**
 * JS File reader.
 * Created by panjiyudasetya on 12/29/16.
 */

public class FileReader {
    /**
     * Utilize js file from resource directory and convert it as String js code.
     *
     * @param name File name
     * @param extension File extension
     * @return String js code
     * @throws IOException Exception
     */
    public String readFile(String name, String extension) throws IOException {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(String.format("%s.%s", name, extension));
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        return new String(buffer, "UTF-8");
    }
}
