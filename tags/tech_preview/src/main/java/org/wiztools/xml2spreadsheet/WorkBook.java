/*
 * WorkBook.java
 *
 * Created on April 6, 2005, 7:54 PM
 */

package org.wiztools.xml2spreadsheet;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author subhash
 */
public interface WorkBook {
    public void write(OutputStream os) throws IOException;
}
