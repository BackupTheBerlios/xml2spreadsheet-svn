/*
 * POIWorkBookCreator.java
 *
 * Created on April 7, 2005, 11:29 AM
 */

package org.wiztools.xml2spreadsheet.poiimpl;

import org.wiztools.xml2spreadsheet.WorkBook;
import org.wiztools.xml2spreadsheet.WorkBookGenerationHandler;
import org.wiztools.xml2spreadsheet.entity.CellEntity;
import org.wiztools.xml2spreadsheet.entity.RowEntity;
import org.wiztools.xml2spreadsheet.entity.SheetEntity;
import org.wiztools.xml2spreadsheet.entity.WorkBookEntity;
import org.wiztools.xml2spreadsheet.exception.OperationException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.wiztools.xml2spreadsheet.util.StyleRepository;


/**
 *
 * @author subhash
 */
public class POIWorkBookCreator implements WorkBookGenerationHandler{
    
    private HSSFCell cell;
    private HSSFRow row;
    private HSSFSheet sheet;
    private HSSFWorkbook workBook;
    private StyleRepository styleRepo;
    
    /** Creates a new instance of POIWorkBookCreator */
    public POIWorkBookCreator() {
    }
    
    public void setCellValue(final String data) throws OperationException{
        this.cell.setCellValue(data);
    }
    
    public void setCellFormula(final String formula) throws OperationException{
        this.cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
        this.cell.setCellFormula(formula);
    }
    
    public void setCellValue(final double data) throws OperationException{
        this.cell.setCellValue(data);
    }
    
    public void setCellValue(final Date date) throws OperationException{
        this.cell.setCellValue(date);
    }
    
    public void setCellValue(final Calendar date) throws OperationException{
        this.cell.setCellValue(date);
    }
    
    public void setCellValue(final boolean data) throws OperationException{
        this.cell.setCellValue(data);
    }
    
    private HSSFCellStyle getStyle(String cellStyleVal) 
                            throws OperationException{
        return POIStyleCreator.getInstance().getStyle(workBook, cellStyleVal, styleRepo);
    }
    
    public void createCell(final CellEntity cell, final short placement) 
                            throws OperationException{
        HSSFCell hcell = this.row.createCell((short)placement);
        
        Map<String, String> attributes = cell.getAttributes();
        if(attributes != null){
            String styleVal = attributes.get("style");
            if(styleVal != null){
                HSSFCellStyle style = getStyle(styleVal);
                hcell.setCellStyle(style);
            }
        }
        this.cell = hcell;
    }
    
    public void createRow(final RowEntity row, final int placement) 
                            throws OperationException{
        HSSFRow hrow = this.sheet.createRow(placement);
        this.row = hrow;
    }
    
    public void mergeCells(final int row1, final short col1, 
            final int row2, final short col2)
                            throws OperationException{
        this.sheet.addMergedRegion(new Region(
                row1, 
                col1, 
                row2, 
                col2));
    }
    
    public void setColumnWidth(final short column, final short width) 
            throws OperationException{
        this.sheet.setColumnWidth(column, width);
    }
    
    public void createSheet(final SheetEntity sheet)
                            throws OperationException{
        Map<String, String> attributes = sheet.getAttributes();
        String name = null;
        if(attributes != null){
            name = attributes.get("name");
        }
        HSSFSheet hsheet = null;
        if(name==null){
            hsheet = this.workBook.createSheet();
        }
        else{
            hsheet = this.workBook.createSheet(name);
        }
        this.sheet = hsheet;
    }
    
    public void createWorkBook(final WorkBookEntity workBook) 
                            throws OperationException{
        HSSFWorkbook hworkBook = new HSSFWorkbook();
        this.workBook = hworkBook;
        
        // A style created on a workbook can be used across
        // all sheets.
        this.styleRepo = new StyleRepository();
    }
    
    public WorkBook getWorkBook() {
        return new POIWorkBookAdapter(this.workBook);
    }
}
