package logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import common.ResultServlet;
import common.UserConnectionData;
import controlResult.EnvironmentStatus;

/**
 * 
 * Guarda el estado del entorno en cuestion en ficheros planos
 *
 */
public class LogControl {

	private UserConnectionData data;
	private String file;
	private BufferedWriter buffWriter;
	private final String connector =" - ";
	
	public LogControl(UserConnectionData data){
		this.data = data;
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
	public void logActivity(ResultServlet result, EnvironmentStatus status){
		String lineLog = getCurrentTime();
		lineLog = lineLog + connector;
		lineLog = lineLog + status.getParsedCurrentStatus(status.getCurrentStatus());
		if(status.getCurrentStatus()== EnvironmentStatus.CURRENT_STATUS_KO){
			lineLog = lineLog + connector + status.getErrorMessage();
		}
		lineLog = lineLog + connector + "Tiempo de respuesta: " + status.getElapsedTime() + " milisegundos.";
		try {
			buffWriter = getLogWriter();
			buffWriter.append(lineLog);
			buffWriter.newLine();
			buffWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logReportFile(ResultServlet result, EnvironmentStatus status, String arqVersion, String version){
		String lineLog = getCurrentTime();
		lineLog = lineLog + connector;
		lineLog = lineLog + status.getParsedCurrentStatus(status.getCurrentStatus());
		if(status.getCurrentStatus()== EnvironmentStatus.CURRENT_STATUS_KO){
			lineLog = lineLog + connector + status.getErrorMessage();
		}
		lineLog = lineLog + connector + "Tiempo de respuesta: " + status.getElapsedTime()
				+ " milisegundos. - Version: " + version +  " - Version Arquitectura: " + arqVersion;
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
		file = file + "/" + env + ".log";
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
}
