/*
 * JXLWorkBookCreator.java
 *
 * Created on April 18, 2005, 8:25 PM
 */

package org.wiztools.xml2spreadsheet.jxlimpl;

import java.util.Map;
import jxl.CellView;
import org.wiztools.xml2spreadsheet.WorkBook;
import org.wiztools.xml2spreadsheet.WorkBookGenerationHandler;
import org.wiztools.xml2spreadsheet.entity.CellEntity;
import org.wiztools.xml2spreadsheet.entity.RowEntity;
import org.wiztools.xml2spreadsheet.entity.SheetEntity;
import org.wiztools.xml2spreadsheet.entity.WorkBookEntity;
import org.wiztools.xml2spreadsheet.exception.OperationException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author subhash
 */
public class JXLWorkBookCreator implements WorkBookGenerationHandler{
    
    private ByteArrayOutputStream baos;
    
    private WritableWorkbook workBook;
    private WritableSheet sheet;
    private int row;
    private int cell;
    
    private int sheetCount;
    
    /** Creates a new instance of JXLWorkBookCreator */
    public JXLWorkBookCreator() {
    }
    
    public void createWorkBook(WorkBookEntity workBook) throws OperationException{
        this.baos = new ByteArrayOutputStream();
        try{
            this.workBook = jxl.Workbook.createWorkbook(baos);
        } catch(IOException ioe){
            assert true:"ByteArrayOutputStream cannot throw IOException";
        }
    }
    public void createSheet(SheetEntity sheet) throws OperationException{
        Map<String, String> attributes = sheet.getAttributes();
        String name = null;
        if(attributes != null){
            name = attributes.get("name");
        }
        if(name == null){
            name = "Sheet " + (sheetCount + 1);
        }
        this.sheet = workBook.createSheet(name, sheetCount);
        sheetCount++;
    }
    public void createRow(RowEntity row, int placement) throws OperationException{
        this.row = placement;
    }
    public void createCell(CellEntity cell, short placement)
    throws OperationException{
        this.cell = placement;
    }
    
    public void setCellValue(String data) throws OperationException{
        Label label = new Label(cell, row, data);
        try{
            sheet.addCell(label);
        } catch(WriteException we){
            throw new OperationException(we.getMessage());
        }
    }
    public void setCellValue(double data) throws OperationException{
        Number number = new Number(cell, row, data);
        try{
            sheet.addCell(number);
        } catch(WriteException we){
            throw new OperationException(we.getMessage());
        }
    }
    public void setCellValue(boolean data) throws OperationException{
        setCellValue(String.valueOf(data));
    }
    public void setCellValue(Date date) throws OperationException{
        DateTime dt = new DateTime(cell, row, date);
        try{
            sheet.addCell(dt);
        } catch(WriteException we){
            throw new OperationException(we.getMessage());
        }
    }
    public void setCellValue(Calendar cal) throws OperationException{
        long l = cal.getTimeInMillis();
        Date date = new Date(l);
        DateTime dt = new DateTime(cell, row, date);
        try{
            sheet.addCell(dt);
        } catch(WriteException we){
            throw new OperationException(we.getMessage());
        }
    }
    public void setCellFormula(String formula) throws OperationException{
        
    }
    
    public void mergeCells(int row1, short col1, int row2, short col2)
    throws OperationException{
        //throw new OperationException("Operation not supported.");
        try{
            sheet.mergeCells(col1, row1, col2, row2);
        } catch(WriteException we){
            throw new OperationException(we.getMessage());
        }
    }
    
    public void setColumnWidth(short column, short width) throws OperationException {
        CellView cv = sheet.getColumnView(column);
        cv.setSize(width);
        // Yes, only if I give this it works:
        sheet.setColumnView(column, cv);
    }
    
    public WorkBook getWorkBook() {
        return new JXLWorkBookAdapter(this.workBook, baos);
    }
    
}
