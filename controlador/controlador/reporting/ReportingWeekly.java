package controlador.reporting;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.commons.io.FileUtils;

import vista.ui.Dialog.MessageCenterDialog;
import conexion.fileAccess.LoadFile;
import conexion.xlsxFileAccess.XlsxAccessFile;
import controlador.common.UserConnectionData;
import controlador.controlResult.GenericStatus;
import controlador.logger.LogLineObject;
import controlador.tools.LogTools;

public class ReportingWeekly implements ReportingClass {
	private Date selectedDate;
	private UserConnectionData data;
	private final static String conn = "-";
	private final static String ext = ".log";
	private final static URL weeklyTemplate = Class.class
			.getResource("/Informe_Semanal.xlsm");
	private static int minHourDay = 8;
	private static int maxHourDay = 19;
	private String reportFileName;
	private String logPath = System.getProperty("user.dir") + "/Logs/";
	private String relativePath = "/Logs/";
	private String fileName;
	private Date[] dayRange;
	private File newReport;
	private XlsxAccessFile xls;

	private final int maxRow = 135;
	private final int maxCol = 5;
	private final int minRow = 2;
	private final int minCol = 1;
	// Media variables
	private int contXAxis = 7;
	private int contYAxis = 2;
	private int accumulatorTime = 0;
	private int ocurrenceNumber = 0;
	private int currentHour = -1;
	private int currentDay = -1;

	public ReportingWeekly(Date generationDate, UserConnectionData user) {
		selectedDate = generationDate;
		data = user;
		relativePath = relativePath + data.getEnvName().replace(" ", "") + "/";
		logPath = logPath + data.getEnvName().replace(" ", "") + "/";
		setNewReportFileName(selectedDate);
		// Segun tipo de informe se inicializa un rango semanal o diario
		initializeRange();
	}

	@Override
	public void generateReport() {
		// Seleccionar el fichero en funcion de la fecha indicada
		if (searchFile() != null) {
			// Se intenta crear el fichero de informe en la ruta de informes
			if (!createReportBase()) {
				MessageCenterDialog.showInformationDialog(null,
						"El informe que esta intentando generar ya existe.");
			} else {
				MessageCenterDialog.showInformationDialog(null, "Generando informe. mantengase a la espera...");
				List<LogLineObject> list = processFile(searchFile());
				writeWeeklyReport(list);
				MessageCenterDialog.showInformationDialog(null, "Se ha generado el informe");
			}
		} else {
			MessageCenterDialog.showInformationDialog(null,
					"No hay datos registrados en esa fecha o para ese entorno");
		}
	}

	private void writeWeeklyReport(List<LogLineObject> list) {
		String title = data.getEnvName();
		int[] rowCol;
		String dayIni = getFormattedDate(dayRange[0], "dd/MM/YYYY");
		String dayEnd = getFormattedDate(dayRange[4], "dd/MM/YYYY");
		title += ". Estado entorno "
				+ dayIni + " - " + dayEnd;
		xls = new XlsxAccessFile(newReport, "Datos");
		xls.setValue(0, 0, title);
		//Se modifican los dias
		setDaysRowName();
		int col = minCol;
		int row = minRow - 1;
		int min = 0;
		for (LogLineObject line : list) {
			if(line.getStatus()!=GenericStatus.CURRENT_STATUS_KO)
				setMediaWeekly(line);
			Date d = line.getFechaValidacion();
			if (dateRange(d)) {
				rowCol = fillCell(row, col, min, line);
				row = rowCol[0];
				col = rowCol[1];
			}
			if (row == maxRow && col == maxCol)
				break;
		}
		setMediaWeekly(null);
		xls.close();
	}

	private int[] fillCell(int row, int col, int min, LogLineObject obj) {
		// Se incrementan las coordenandas de la matriz
		boolean fin = false;
		int[] rowCol;
		int incr = 5;
		int rowMinute = 0;
		int rowDay = 0;
		int currentMinute = Integer.valueOf(getFormattedDate(
				obj.getFechaValidacion(), "mm"));
		int currentHour = Integer.valueOf(getFormattedDate(
				obj.getFechaValidacion(), "HH"));
		int currentDay = Integer.valueOf(getFormattedDate(
				obj.getFechaValidacion(), "dd"));
		rowDay = Integer.valueOf(getFormattedDate(dayRange[col-1], "dd"));
		rowCol = incrCoord(row, col);
		row = rowCol[0];
		col = rowCol[1];
		do {
			int rowHour = getRowHour(row);
			rowMinute = getRowMinute(row);
			if (checkMin(currentMinute, rowMinute, incr)
					&& checkHour(currentHour, rowHour)
					&& checkDay(currentDay, rowDay)) {
				// Dentro de rango
				fin = true;
				xls.setValue(row, col, getStatusString(obj.getStatus()));
			} else if ((currentMinute < rowMinute && currentHour == rowHour && currentDay == rowDay)
					|| (currentHour < rowHour && currentDay == rowDay)
					|| currentDay < rowDay) {
				// Menor
				fin = true;
				rowCol = decreaseCoord(row, col);
				row = rowCol[0];
				col = rowCol[1];
				rowDay = Integer.valueOf(getFormattedDate(dayRange[col-1], "dd"));
				rowHour = getRowHour(row);
			} else {
				// Mayor
				fin = false;
				rowCol = incrCoord(row, col);
				row = rowCol[0];
				col = rowCol[1];
				rowDay = Integer.valueOf(getFormattedDate(dayRange[col-1], "dd"));
				rowHour = getRowHour(row);
			}
		} while (!fin);
		return rowCol;
	}

	private boolean checkMin(int currentMin, int initMin, int incr) {
		if (currentMin >= initMin && currentMin < initMin + incr)
			return true;
		return false;
	}

	private boolean checkHour(int currentHour, int hour) {
		if (currentHour == hour)
			return true;
		return false;
	}

	private boolean checkDay(int currentDay, int day) {
		if (currentDay == day)
			return true;
		return false;
	}

	private int getRowMinute(int row){
		int time = 5;
		int maxMin= 60;
		int result = (time*(row-1))%maxMin;
		return result;
	}
	
	private int getRowHour(int row) {
		// Calcula la hora que le corresponde a la fila pasada
		int constH = 11;
		int result = 0;
		result = ((row + constH) / 12) + 7;
		return result;
	}

	private int[] decreaseCoord(int row, int col) {
		int[] result = new int[2];
		if (row == minRow && col > minCol) {
			result[0] = maxRow;
			result[1] = col - 1;
		} else{
			result[0] = row - 1;
			result[1] = col;
		}
		return result;
	}

	private int[] incrCoord(int row, int col) {
		int[] result = new int[2];
		if (row == maxRow) {
			result[0] = minRow;
			result[1] = col + 1;
		} else {
			result[0] = row + 1;
			result[1] = col;
		}
		return result;
	}

	private List<LogLineObject> processFile(Object searchFile) {
		HashMap<Integer, String> fileList = LoadFile.getFileMap(relativePath,
				fileName);
		List<LogLineObject> list = new Vector<LogLineObject>();
		int line = 0;
		LogLineObject obj;
		while (line < fileList.size()) {
			// Se procesa linea
			obj = LogTools.deserializedLogLine(fileList.get(line));
			for (int i = 0; i < dayRange.length; i++) {
				// Comprueba si la fecha esta dentro del rango valido
				if (compareDays(dayRange[i], obj.getFechaValidacion())
						&& validDateTime(obj.getFechaValidacion()))
					list.add(obj);
			}
			line++;
		}
		return list;
	}

	private boolean createReportBase() {
		String env = data.getEnvName().replace(" ", "");
		String path = System.getProperty("user.dir") + "/Informes/" + env + "/"
				+ this.reportFileName;
		newReport = new File(path);
		if (newReport.exists())
			return false;
		try {
			FileUtils.copyURLToFile(weeklyTemplate, newReport);
		} catch (IOException e) {
			MessageCenterDialog
					.showErrorDialog(null,
							"Se ha producido un error inesperado, contacte con el Administrador");
			e.printStackTrace();
		}
		return true;
	}

	private Object searchFile() {
		String week = getFormattedDate(selectedDate, "ww");
		String year = getFormattedDate(selectedDate, "YYYY");
		String envName = data.getEnvName().replace(" ", "");
		// Construye el nombre completo del log
		fileName = envName + conn + "W" + conn + week + conn + year + ext;
		// Comprueba que el fichero exista
		File file = new File(logPath + fileName);
		if (file.exists())
			return file;
		else
			return null;
	}

	private void initializeRange() {
		dayRange = new Date[5];
		int week = Integer.valueOf(getFormattedDate(selectedDate, "ww"));
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.WEEK_OF_YEAR, week);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		for (int i = 0; i < 5; i++) {
			dayRange[i] = cal.getTime();
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
	}

	private void setNewReportFileName(Date date) {
		// W-52-2017-Informe_Semanal.xlsm
		String fileName = "";
		String envName = data.getEnvName().replace(" ", "");
		fileName = getFormattedDate(date, "'W'-ww-YYYY-'Informe_Semanal-'");
		fileName += envName + ".xlsm";
		reportFileName = fileName;
	}

	private boolean validDateTime(Date date) {
		int hour = Integer.valueOf(getFormattedDate(date, "HH"));
		if (hour < minHourDay || hour > maxHourDay)
			return false;
		return true;
	}

	/**
	 * Determina si la fecha se encuentra dentro del rango de estudio
	 * 
	 * @param date
	 * @return
	 */
	private boolean dateRange(Date date) {
		// La hora debe estar entre las 8:00 y las 20:00
		int hour = Integer.valueOf(getFormattedDate(date, "HH"));
		if (hour < 8 || hour > 19)
			return false;
		return true;
	}

	private boolean compareDays(Date date1, Date date2) {
		String day1, month1, year1, day2, month2, year2;
		day1 = getFormattedDate(date1, "dd");
		day2 = getFormattedDate(date2, "dd");
		month1 = getFormattedDate(date1, "MM");
		month2 = getFormattedDate(date2, "MM");
		year1 = getFormattedDate(date1, "YY");
		year2 = getFormattedDate(date2, "YY");
		if (day1.equals(day2) && month1.equals(month2) && year1.equals(year2))
			return true;
		return false;
	}

	private String getFormattedDate(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	private String getStatusString(int status) {
		String result;
		switch (status) {
		case GenericStatus.CURRENT_STATUS_OK:
			result = GenericStatus.CURRENT_STATUS_OK_STRING;
			break;
		case GenericStatus.CURRENT_STATUS_KO:
			result = GenericStatus.CURRENT_STATUS_KO_STRING;
			break;
		case GenericStatus.CURRENT_STATUS_PDT:
			result = GenericStatus.CURRENT_STATUS_PDT_STRING;
			break;
		case GenericStatus.CURRENT_STATUS_REV:
			result = GenericStatus.CURRENT_STATUS_OK_STRING;
			break;
		default:
			result = GenericStatus.CURRENT_STATUS_UNKNOWN_STRING;
			break;
		}
		return result;
	}
	
	private void setDaysRowName(){
		int colMediaTitle = 7;
		int firstRowMediaTitle = 2;
		int firstColStatusTitle = 1;
		int RowStatusTitle = 1;
		for(int i=0;i< dayRange.length;i++){
			String value = "Dia " + getFormattedDate(dayRange[i],"dd");
			xls.setValue(firstRowMediaTitle, colMediaTitle, value);
			xls.setValue(RowStatusTitle, firstColStatusTitle, value);
			firstRowMediaTitle++;
			firstColStatusTitle++;
		}
	}
	
	/**
	 * Calcula la media de respuesta de un entorno por horas
	 * 
	 * @param log
	 */
	private void setMediaWeekly(LogLineObject log) {
		if(log==null){
			int media = accumulatorTime/ocurrenceNumber;
			contXAxis++;
			xls.setValue(contYAxis, contXAxis, media);
			return;
		}
		int hour = Integer.valueOf(getFormattedDate(log.getFechaValidacion(), "HH"));
		int day = Integer.valueOf(getFormattedDate(log.getFechaValidacion(), "dd"));
		if(currentDay == -1){
			currentDay = day;
			currentHour = hour;
		}
		if(day != currentDay){
			//Si hay cambio del día escribe xls resetea contadores
			// y avanza fila
			int media = accumulatorTime/ocurrenceNumber;
			xls.setValue(contYAxis, contXAxis, media);
			contXAxis = 7;
			contYAxis++;
			accumulatorTime = log.getResponseTime();
			ocurrenceNumber = 1;
			currentDay = day;
		}else if(hour!=currentHour){
			//En caso de cambio de hora, calcula resultado final,
			//se guarda y se resetea contadores
			int media = accumulatorTime/ocurrenceNumber;
			contXAxis++;
			xls.setValue(contYAxis, contXAxis, media);
			currentHour = hour;
			accumulatorTime = log.getResponseTime();
			ocurrenceNumber = 1;
		}else if(hour==currentHour && day == currentDay){
			accumulatorTime += log.getResponseTime();
			ocurrenceNumber++;
		}
	}
}
