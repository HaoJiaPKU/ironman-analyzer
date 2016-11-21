package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XSSFInput {
	
	public InputStream is;
	public XSSFWorkbook xssfWorkbook;
	
	public int sheetCur = 0;
	public XSSFSheet xssfSheet;
	public int rowCur = 0;
	public XSSFRow xssfRow;
	
	public XSSFInput (String inputPath) {
		initInput(inputPath);
	}
	
	public void initInput(String inputPath)
	{
		try {
			this.is = new FileInputStream(new File(inputPath));
			this.xssfWorkbook = new XSSFWorkbook(is);
			this.xssfSheet = xssfWorkbook.getSheetAt(this.sheetCur);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setCurAtSheet(int number) {
		if (number >= 0 && number < this.xssfWorkbook.getNumberOfSheets()) {
			this.sheetCur = number;
		}
		this.xssfSheet = this.xssfWorkbook.getSheetAt(this.sheetCur);
	}
	
	public String[] getNextRow() {
		if (this.rowCur <= this.xssfSheet.getLastRowNum()) {
			this.xssfRow = xssfSheet.getRow(this.rowCur);
			String ret[] = new String[this.xssfRow.getLastCellNum()];
			for (int i = 0; i < this.xssfRow.getLastCellNum(); i ++) {
				if (this.xssfRow.getCell(i) != null) {
					ret[i] = getValue(this.xssfRow.getCell(i));
				}
			}
			this.rowCur ++;
			return ret;
		}
		return null;
	}
	
	private String getValue(XSSFCell xssfCell) {
        if (xssfCell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xssfCell.getBooleanCellValue());
        } else if (xssfCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return String.valueOf(xssfCell.getNumericCellValue());
        } else {
            return String.valueOf(xssfCell.getStringCellValue());
        }
    }
	
	public static void main(String [] args) {}
}
