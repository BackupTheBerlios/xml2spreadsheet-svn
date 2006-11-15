/*
 * JXLWorkBookAdapter.java
 *
 * Created on April 18, 2005, 8:12 PM
 */

package org.wiztools.xml2spreadsheet.jxlimpl;

import org.wiztools.xml2spreadsheet.WorkBook;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import jxl.write.WritableWorkbook;

/**
 *
 * @author subhash
 */
public class JXLWorkBookAdapter implements WorkBook{
    
    private WritableWorkbook workBook;
    private ByteArrayOutputStream baos;
    
    /** Creates a new instance of JXLWorkBookAdapter */
    public JXLWorkBookAdapter(WritableWorkbook workBook,
            ByteArrayOutputStream baos) {
        this.workBook = workBook;
        this.baos = baos;
    }
    
    public void write(OutputStream os) throws IOException{
        workBook.write();
        workBook.close();
        baos.writeTo(os);
    }
    
}
