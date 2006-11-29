/*
 * WorkBookGenerationHandler.java
 *
 * Created on April 7, 2005, 6:14 PM
 */

package org.wiztools.xml2spreadsheet;

import org.wiztools.xml2spreadsheet.entity.CellEntity;
import org.wiztools.xml2spreadsheet.entity.RowEntity;
import org.wiztools.xml2spreadsheet.entity.SheetEntity;
import org.wiztools.xml2spreadsheet.entity.WorkBookEntity;
import org.wiztools.xml2spreadsheet.exception.OperationException;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author subhash
 */
public interface WorkBookGenerationHandler extends WorkBookCreator{
    public void createWorkBook(WorkBookEntity workBook) throws OperationException;
    public void createSheet(SheetEntity sheet) throws OperationException;
    public void createRow(RowEntity row, int placement) throws OperationException;
    public void createCell(CellEntity cell, short placement) 
                    throws OperationException;
    
    public void setCellValue(String data) throws OperationException;
    public void setCellValue(double data) throws OperationException;
    public void setCellValue(boolean data) throws OperationException;
    public void setCellValue(Date date) throws OperationException;
    public void setCellValue(Calendar date) throws OperationException;
    public void setCellFormula(String formula) throws OperationException;
    
    public void mergeCells(int row1, short col1, int row2, short col2) 
                    throws OperationException;
    
    public void setColumnWidth(short column, short width) throws OperationException;
}
