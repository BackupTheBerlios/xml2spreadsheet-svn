/*
 * WorkBookCreator.java
 *
 * Created on April 7, 2005, 11:24 AM
 */

package org.wiztools.xml2spreadsheet;

import org.wiztools.xml2spreadsheet.exception.XML2XLSFatalException;
import java.io.InputStream;

/**
 *
 * @author subhash
 */
public interface WorkBookCreator {
    public WorkBook getWorkBook();
}
