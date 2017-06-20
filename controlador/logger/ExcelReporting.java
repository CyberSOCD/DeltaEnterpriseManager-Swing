package logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public class ExcelReporting {

	private HSSFWorkbook wb;
	private HSSFSheet sheet;
	private int rowNumber = 1;
	private static final String sheetName = "DATOS";
	private final String connector =" - ";
	private String file;
	private final int fecha = 0;
	private final int resultado = 1;
	private final int error = 2;
	private final int tiempo = 3;
	private FileInputStream in;
	private boolean appendFile = false;
	
	/**
	 * Recibe los datos necesarios para crear o abrir si fuese necesario un log
	 * @param file
	 * @throws IOException 
	 */
	public ExcelReporting(String file) throws IOException{
		//Crea en caso de ser necesario el libro del log
		initializeWorkbook(file);
	}
	
	public void writeLine(String logInfo){
		HSSFRow row = sheet.createRow(rowNumber);
		int ColumnCount = 0;
		for(String word:logInfo.split(connector)){
			row.createCell(ColumnCount).setCellValue(word);
			ColumnCount++;
		}
		rowNumber++;
	}
	
	/**
	 * Escribe el workbook creado
	 */
	public void stopValidation(){
		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			if(appendFile){
				System.out.println("Existe el xls :::::::: " + file);
				in.close();
			}
			wb.write(fileOut);
			fileOut.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Crea o reutiliza un libro Excel para almacenar los datos de log
	 * @throws IOException 
	 */
	private void initializeWorkbook(String file) throws IOException{
		this.file = file;
		if(new File(file).exists()){
			System.out.println("Existe el xls :::::::: " + file);
			//En caso de existir se carga el libro actual y asigna numero de file
			in = new FileInputStream(new File(file));
			wb = new HSSFWorkbook(in);
			sheet = wb.getSheet(sheetName);
			rowNumber = getRowNumer(sheet);
			appendFile = true;
		}else{
			wb = new HSSFWorkbook();
			sheet = wb.createSheet(sheetName);
			HSSFRow  r = sheet.createRow(0);
			r.createCell(fecha).setCellValue("Fecha");
			r.createCell(resultado).setCellValue("Estado");
			r.createCell(error).setCellValue("Mensaje de Error");
			r.createCell(tiempo).setCellValue("Tiempo respuesta del Servidor");
		}
	}
	
//	/**
//	 * Comprueba si el fichero esta en uso
//	 * @param file
//	 * @return
//	 */
//	private boolean validateFile(File file){
//		boolean fileUse = false;
//		try {
//			org.apache.commons.io.FileUtils.touch(file);
//		} catch (IOException e) {
//			fileUse = true;
//		}
//		return fileUse;
//	}
	
	/**
	 * Recupera el numero de fila valido a partir 
	 * del que se puede introducir datos
	 * @param sht
	 * @return
	 */
	private int getRowNumer(HSSFSheet sht){
		return sht.getLastRowNum() + 1;
	}
}
