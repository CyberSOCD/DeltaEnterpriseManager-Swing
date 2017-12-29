package controlador.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import controlador.common.ResultServlet;
import controlador.common.UserConnectionData;
import controlador.controlResult.EnvironmentStatus;
import controlador.tools.LogTools;

/**
 * 
 * Guarda el estado del entorno en cuestion en ficheros planos
 *
 */
public class LogControl {

	/***************************************************************************************
	 * Se debe implantar una rotacion de Log semanal para facilitar busqueda y reducir
	 * consumo buscando el rango de fecha exacto
	 **************************************************************************************/
	
	private UserConnectionData data;
	private String file;
	private BufferedWriter buffWriter;
	private final String connector =" - ";
	private LogLineObject logObject;
	
	public LogControl(UserConnectionData data){
		this.data = data;
	}
	
	public void activeLog(){
		try {
			buffWriter = getLogWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Escribe la linea del log
	 * @param result
	 * @param status
	 * @param string
	 */
	public void logActivity(ResultServlet result, EnvironmentStatus status, String serverVersion, String arqVersion){
		String lineLog;
		logObject = new LogLineObject();
		logObject.setValidationDate(new Date());
		logObject.setStatus(status.getCurrentStatus());
		logObject.setResponseTime((int) status.getElapsedTime());
		logObject.setErrorMessage(status.getErrorMessage());
		logObject.setServerVersion(serverVersion);
		logObject.setArqVersion(arqVersion);
		lineLog = LogTools.serializeLineLog(logObject);
//		lineLog = lineLog + connector;
//		lineLog = lineLog + status.getParsedCurrentStatus(status.getCurrentStatus());
//		if(status.getCurrentStatus()== EnvironmentStatus.CURRENT_STATUS_KO){
//			lineLog = lineLog + connector + status.getErrorMessage();
//		}
//		lineLog = lineLog + connector + "Tiempo de respuesta: " + status.getElapsedTime() + " milisegundos.";
		try {
			buffWriter = getLogWriter();
			buffWriter.append(lineLog);
			buffWriter.newLine();
			buffWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Define la ruta del fichero donde se guardara la actividad
	 */
	private void setFilePath(){
		String env = data.getEnvName().replace(" ", "");
		file = System.getProperty("user.dir") + "/Logs/" + env;
		new File(file).mkdirs();
		file = file + "/" + env + getWeek() + ".log";
	}
	
	/**
	 * Devuelve el buffer para escribir sobre el log 
	 * @throws IOException 
	 */
	private BufferedWriter getLogWriter() throws IOException{
		setFilePath();
		FileWriter wr = new FileWriter(file, true);
		BufferedWriter bf = new BufferedWriter(wr);
		return bf;
	}
	
	/**
	 * Devuelve fecha/hora actual
	 * @return
	 */
	private String getCurrentTime(){
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
        return sdf.format(cal.getTime());
	}
	
	/**
	 * Devuelve el mes y el año actual formateados
	 * @return
	 */
	private String getMonthYear(){
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("-MM-yyyy");
        
		try {
			return sdf.format(sdf.parse("-01-2018"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return sdf.format(cal.getTime());
		}
	}
	
	private String getWeek(){
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("-'W'-ww-yyyy");
        return sdf.format(cal.getTime());
        
//        try {
//			return sdf.format(sdf.parse("-01-2018"));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return sdf.format(cal.getTime());
//		}
	}
}
