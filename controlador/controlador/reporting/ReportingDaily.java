package controlador.reporting;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
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

public class ReportingDaily implements ReportingClass {

	private Date selectedDate;
	private UserConnectionData data;
	private final static String conn = "-";
	private final static String ext = ".log";
	private final static URL dailyTemplate = Class.class
			.getResource("/Informe_Diario.xlsm");
	private static int minTimeDay = 8;
	private static int maxTimeDay = 19;
	private String reportFileName;
	private String logPath = System.getProperty("user.dir") + "/Logs/";
	private String relativePath = "/Logs/";
	private String fileName;
	private Date[] dayRange;
	private File newReport;
	private XlsxAccessFile xls;
	private final int maxRow = 13;
	private final int maxCol = 12;
	private final int minRow = 2;
	private final int minCol = 1;
	//Media variables
	private int accumulatorTime = 0;
	private int ocurrenceNumber = 0;
	private int currentHour = -1;
	private int contArr = 0;

	public ReportingDaily(Date generationDate, UserConnectionData user) {
		selectedDate = generationDate;
		data = user;
		relativePath = relativePath + data.getEnvName().replace(" ", "") + "/";
		logPath = logPath + data.getEnvName().replace(" ", "") + "/";
		setNewReportFileName(selectedDate);
		// Segun tipo de informe se inicializa un rango semanal o diario
		initializeRange();
	}

	/**
	 * Genera el informe para la fecha seleccionada en la modalidad seleccionada
	 */
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
				writeXlsx(list);
				MessageCenterDialog.showInformationDialog(null, "Se ha generado el informe");
			}
		} else {
			MessageCenterDialog.showInformationDialog(null,
					"No hay datos registrados en esa fecha o para ese entorno");
		}
	}

	/**
	 * Intenta crear fichero para informe
	 */
	private boolean createReportBase() {
		String env = data.getEnvName().replace(" ", "");
		String path = System.getProperty("user.dir") + "/Informes/" + env + "/"
				+ this.reportFileName;
		newReport = new File(path);
		if (newReport.exists())
			return false;
		try {
			FileUtils.copyURLToFile(dailyTemplate, newReport);
		} catch (IOException e) {
			MessageCenterDialog
					.showErrorDialog(null,
							"Se ha producido un error inesperado, contacte con el Administrador, ");
		}
		return true;
	}

	private File searchFile() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("ww");
		String week = dateFormat.format(selectedDate);
		dateFormat = new SimpleDateFormat("YYYY");
		String year = dateFormat.format(selectedDate);
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

	private List<LogLineObject> processFile(File file) {
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

	private void setNewReportFileName(Date date) {
		// 01-01-2018-Informe_Diario.xlsm
		String fileName = "";
		String envName = data.getEnvName().replace(" ", "");
		SimpleDateFormat sdfDialy = new SimpleDateFormat(
				"dd-MM-YYYY-'InformeDiario-'");
		fileName = sdfDialy.format(date);
		fileName += envName + ".xlsm";
		reportFileName = fileName;

	}

	private boolean compareDays(Date date1, Date date2) {
		String day1, month1, year1, day2, month2, year2;
		SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
		SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
		SimpleDateFormat sdfYear = new SimpleDateFormat("YY");
		day1 = sdfDay.format(date1);
		day2 = sdfDay.format(date2);
		month1 = sdfMonth.format(date1);
		month2 = sdfMonth.format(date2);
		year1 = sdfYear.format(date1);
		year2 = sdfYear.format(date2);
		if (day1.equals(day2) && month1.equals(month2) && year1.equals(year2))
			return true;
		return false;
	}

	/**
	 * Valida si la fecha pasada esta en horario de oficina informe
	 * 
	 * @return
	 */
	private boolean validDateTime(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH");
		int hour = Integer.valueOf(sdf.format(date));
		if (hour < minTimeDay || hour > maxTimeDay)
			return false;
		return true;
	}

	/**
	 * Segun el tipo de informe seleccionado se almacen al rango de dias que
	 * aplican para el informe
	 */
	private void initializeRange() {
		dayRange = new Date[1];
		dayRange[0] = selectedDate;
	}

	/**
	 * Invoca al Job correspondiente para generar el tipo de informe
	 * seleccionado
	 * 
	 * @param list
	 */
	private void writeXlsx(List<LogLineObject> list) {
		writeDailyReport(list);
	}

	private void writeDailyReport(List<LogLineObject> list) {
		String title = data.getEnvName();
		int[] rowCol;
		title = " Estado entorno " + data.getEnvName() + " - "
				+ getFormattedDate(selectedDate, "dd/MM/YYYY");
		xls = new XlsxAccessFile(newReport, "Datos");
		xls.setValue(0, 0, title);
		String timeOutDay = "Dia " + getFormattedDate(selectedDate, "dd");
		xls.setValue(18, 0, timeOutDay);
		int col = minCol;
		int row = minRow - 1;
		int min = 0;
		for (LogLineObject line : list) {
			if(currentHour==-1)
				currentHour = Integer.valueOf(getFormattedDate(line.getFechaValidacion(),"HH"));
			if(line.getStatus()!=GenericStatus.CURRENT_STATUS_KO)
				setMediaDaily(line);
			Date d = line.getFechaValidacion();
			if (dateRange(d)) {
				rowCol = fillCell(row, col, min, line);
				row = rowCol[0];
				col = rowCol[1];
			}
			// Esta al limite de la matriz???? break
			if (row == maxRow && col == maxCol)
				break;
		}
		setMediaDaily(null);
		xls.close();
	}

	/**
	 * Rellena las celdas que correspondan y devuelve las coordenadas actuales
	 * 
	 * @param row
	 * @param col
	 * @param min
	 * @param date
	 * @return
	 */
	private int[] fillCell(int row, int col, int min, LogLineObject obj) {
		// Se incrementan las coordenandas de la matriz
		boolean fin = false;
		int[] rowCol;
		int incr = 5;
		int rowMinute = 0;
		int currentMinute = Integer.valueOf(getFormattedDate(
				obj.getFechaValidacion(), "mm"));
		int currentHour = Integer.valueOf(getFormattedDate(
				obj.getFechaValidacion(), "HH"));
		rowCol = incrCoord(row, col);
		row = rowCol[0];
		col = rowCol[1];
		do {
			int rowHour = minTimeDay + col - minCol;
			rowMinute = (row - minRow) * incr;
			if (currentMinute >= rowMinute && currentMinute < rowMinute + incr
					&& rowHour == currentHour) {
				// Dentro de rango
				fin = true;
				xls.setValue(row, col, getStatusString(obj.getStatus()));
			} else if ((currentMinute < rowMinute && currentHour == rowHour)
					|| currentHour < rowHour) {
				// Menor
				fin = true;
				rowCol = decreaseCoord(row, col);
				row = rowCol[0];
				col = rowCol[1];
			} else if ((currentMinute >= rowMinute + incr && currentHour == rowHour)
					|| currentHour > rowHour) {
				// Mayor
				fin = false;
				rowHour = minTimeDay + col - minCol;
				rowCol = incrCoord(row, col);
				row = rowCol[0];
				col = rowCol[1];
			}
		} while (!fin);
		return rowCol;
	}

	/**
	 * Avanza las coordenadas de la matriz
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
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

	private int[] decreaseCoord(int row, int col) {
		int[] result = new int[2];
		if (row == minRow) {
			result[0] = maxRow;
			result[1] = col - 1;
		} else {
			result[0] = row - 1;
			result[1] = col;
		}
		return result;
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

	private String getFormattedDate(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
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
	
	/**
	 * Calcula la media de respuesta de un entorno por horas
	 * 
	 * @param log
	 */
	private void setMediaDaily(LogLineObject log) {
		if(log==null){
			//Final del calculo de media recopilamos informacion de la ultima
			int media = accumulatorTime/ocurrenceNumber;
			contArr++;
			xls.setValue(18, contArr, media);
			return;
		}
		int hour = Integer.valueOf(getFormattedDate(log.getFechaValidacion(), "HH"));
		if(hour!=currentHour){
			//En caso de cambio de hora, calcula resultado final,
			//se guarda y se resetea contadores
			int media = accumulatorTime/ocurrenceNumber;
			contArr++;
			accumulatorTime = 0;
			ocurrenceNumber = 0;
			xls.setValue(18, contArr, media);
			currentHour = hour;
			accumulatorTime += log.getResponseTime();
			ocurrenceNumber++;
		}else{
			accumulatorTime += log.getResponseTime();
			ocurrenceNumber++;
		}
	}
}
