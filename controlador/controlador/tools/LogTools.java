package controlador.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import controlador.controlResult.GenericStatus.CurrentStatusValue;
import controlador.logger.LogLineObject;

public class LogTools {

	// private LogLineObject log;
	// private static String logPath;

	// Constantes
	private static String connector = "&";
	private static int datePosition = 0;
	private static int statusPosition = 1;
	private static int errorMsgPosition = 2;
	private static int simpleTimePosition = 2;
	private static int errorTimePosition = 3;

	/**
	 * Transforma una linea de log en un Objecto @LogLineObject
	 * 
	 * @param line
	 * @return lineObject
	 * @throws ParseException
	 */
	public static LogLineObject deserializedLogLine(String line)
			throws ParseException {
		LogLineObject lineObject = new LogLineObject();
		boolean errorLine = false;
		int timeValue;
		errorLine = line.split(connector).length == 4;
		String dateString = line.split(connector)[datePosition];
		String statusString = line.split(connector)[statusPosition];
		String errorString = "";
		String timeString;
		if (errorLine) {
			errorString = line.split(connector)[errorMsgPosition];
			timeString = line.split(connector)[errorTimePosition];
		} else {
			timeString = line.split(connector)[simpleTimePosition];
		}
		// Tratamiento de fecha
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
		lineObject.setValidationDate(sdf.parse(dateString));
		// Tratamiento status
		lineObject.setStatus(Integer.valueOf(statusString));
		if (errorLine)
			lineObject.setErrorMessage(errorString);
		// Tratamiento del tiempo de respuesta
		timeString = timeString.replace("Tiempo de respuesta: ", "").replace(
				" milisegundos.", "");
		timeValue = Integer.parseInt(timeString);
		// timeValue = Integer.getInteger();
		lineObject.setResponseTime(timeValue);
		return lineObject;
	}

	/**
	 * Transforma en un String el objeto pasado
	 * 
	 * @param lineObject
	 * @return
	 */
	public static String serializeLineLog(LogLineObject lineObject) {
		String logLine = "";
		logLine = Long.toString(lineObject.getFechaValidacion().getTime());
		logLine += connector;
		logLine += lineObject.getStatus();
		logLine += connector;
		if(lineObject.isError()){
			logLine += lineObject.getErrorMessage();
			logLine += connector;
		}else{
			logLine += lineObject.getServerVersion();
			logLine += connector;
			logLine += lineObject.getArqVersion();
			logLine += connector;
		}
		logLine += lineObject.getResponseTime();
		return logLine;
	}

}
