package conexion.xlsxFileAccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XlsxAccessFile {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private File file;
	private String filePath;
	private String fileName;
	
	/**
	 * Constructor prepara el fichero a rellenar
	 * @param sheetName
	 * @param path
	 * @param name
	 */
	public XlsxAccessFile(File file, String sheetName){
		FileInputStream in;
		this.file = file;
		try {
			in = new FileInputStream(file);
			workbook = new XSSFWorkbook(in);
			sheet = workbook.getSheet(sheetName);
		}catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setValue(int row, int col, String value){
		if(sheet.getRow(row) == null){
			sheet.createRow(row);
		}
		if(sheet.getRow(row).getCell(col) == null)
			sheet.getRow(row).createCell(col);
		sheet.getRow(row).getCell(col).setCellValue(value);
		try {
			workbook.write(new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setValue(int row, int col, int value){
		if(sheet.getRow(row)==null)
			sheet.createRow(row);
		if(sheet.getRow(row).getCell(col) == null)
			sheet.getRow(row).createCell(col);
		sheet.getRow(row).getCell(col).setCellValue(value);
		try {
			workbook.write(new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close(){
		try {
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
