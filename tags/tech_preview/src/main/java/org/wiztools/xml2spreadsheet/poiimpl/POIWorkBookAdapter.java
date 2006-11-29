/*
 * WorkBookPOI.java
 *
 * Created on April 6, 2005, 7:57 PM
 */

package org.wiztools.xml2spreadsheet.poiimpl;

import org.wiztools.xml2spreadsheet.WorkBook;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author subhash
 */
public class POIWorkBookAdapter implements WorkBook{
    
    private HSSFWorkbook workBook;
    
    /** Creates a new instance of WorkBookPOI */
    public POIWorkBookAdapter(HSSFWorkbook workBook) {
        this.workBook = workBook;
    }
    
    public void write(OutputStream os) throws IOException{
        workBook.write(os);
    }
}
