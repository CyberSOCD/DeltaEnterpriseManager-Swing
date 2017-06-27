package validationPackage;

import java.net.MalformedURLException;

import xmlCheck.CheckXmlLogin;
import logger.LogControl;
import builders.XmlBuilder;
import common.ResultServlet;
import common.UserConnectionData;
import controlResult.EnvironmentStatus;
import envAccess.EnvAccess;

/**
 * 
 * Se encarga de relizar las peticiones necesarias para validar el estado del entorno
 * Para validar el estado realiza una llamada al servlet loginExplicit del entorno
 * En funcion de la respuesta del servidor se cambiará el estado de la clase
 *
 */
public class EnvStatusValidation extends Validation{
	private ResultServlet returnValue;
	private UserConnectionData data;
	private final String servletConection = "/servlet/isf.servlets.gcxs.SessionServlet";
	private final String servletArqVersion = "/servlet/isf.servlets.gcxs.InitialServicesServlet";
	private final String loginMethod = "loginExplicit";
	private final String arqMethod = "getInitialServices";
//	private String envVersion = "";
//	private String arqVersion = "";
	private EnvironmentStatus status;
	private LogControl Log;
	private boolean first;

	public EnvStatusValidation(UserConnectionData data){
		this.data = data;
		this.status = new EnvironmentStatus();
		Log = new LogControl(data);
		first = true;
	}
	
	@Override
	public void validate() throws Exception, MalformedURLException {
		long elapsedTime = 0;
		long prevTime = 0;
		returnValue = null;
		//Crea el objeto que recupera el xml a enviar
		XmlBuilder builder = new XmlBuilder(data, loginMethod);
		//Crea el objecto que va a trabajar sobre el servlet
		EnvAccess env;
		if(first){
			env = new EnvAccess(data, builder.getXml().replace("deleteOtherSessions=\"true\"", "deleteOtherSessions=\"false\""),servletConection);
			first = false;
		}else{
			env = new EnvAccess(data, builder.getXml(),servletConection);
		}
		prevTime = System.nanoTime();
		//Realiza el envio al servlet
		returnValue = env.invokeServlet();
		long newTime = System.nanoTime();
		elapsedTime = (newTime - prevTime)/1000000;
		if(prevTime != 0){
			status.setElapsedTime(elapsedTime);
		}else{
			status.setElapsedTime(-1);
		}
		//Si no hay excepciones se busca error controlado por la aplicacion
		if(returnValue.getErrorMessage().isEmpty()){
			//Se analiza el xml devuelto para determinar estado de entorno
			CheckXmlLogin check = new CheckXmlLogin(returnValue);
			if(check.validarResultado()){
				status.setCurrentStatus(EnvironmentStatus.CURRENT_STATUS_OK);
				status.setErrorMessage("");
				if(status.getElapsedTime()>5000){
					status.setCurrentStatus(EnvironmentStatus.CURRENT_STATUS_REV);
					status.setErrorMessage("El tiempo de respuesta del servidor es lento");
				}
			}else{
//				System.out.println("-- Fallo marcado por el checkXml ");
//				System.out.println(returnValue.getErrorMessage());
				status.setCurrentStatus(EnvironmentStatus.CURRENT_STATUS_KO);
				status.setErrorMessage(check.getErrorMessage());
			}
		}else{
//			System.out.println("-- Fallo hay mensaje en returnValue");
//			System.out.println(returnValue.getErrorMessage());
			status.setCurrentStatus(EnvironmentStatus.CURRENT_STATUS_KO);
			status.setErrorMessage(returnValue.getErrorMessage());
		}
	}
	
	public void activate(){
		Log.activeLog();		
	}
	
	public String getServerVersion() throws Exception{
		String value = "";
		EnvAccess env = new EnvAccess(data);
		try{
			value = env.invokeHtml().getByXPath("//h2/text()").get(0).toString();
		}catch(Exception e) {
			//En caso de excepcion se devuelve vacio
			value = "";
		}
		return value.replace("Versión desplegada ", "");
	}
	
	public void stopValidation(){
		Log.stopValidation();
	}
	
	public void resumeValidation(){
		Log.resumeValidation();
	}
	
	public String getArqVersion(){
		String value = "";
		CheckXmlLogin check;
		//Crea el objeto que recupera el xml a enviar
		XmlBuilder builder = new XmlBuilder(data, arqMethod);
		try{
			EnvAccess env = new EnvAccess(data, builder.getXml(),servletArqVersion);
			env.invokeServlet();
			//Realiza el envio al servlet
			check = new CheckXmlLogin(env.invokeServlet());
			value = check.getArqVersion();
		}catch(Exception e){
			//En caso de excepcion se devuelve vacio
			value = "";
		}
		return value;
	}

	@Override
	public EnvironmentStatus getCurrentStatus(){
		return status;
	}

	@Override
	public void logActivity() {
		Log.logActivity(returnValue, status);
//		try {
//			Log.logReportFile(returnValue, status,getArqVersion(),getServerVersion());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
}
