package controlador.validationPackage;

import conexion.envAccess.EnvAccess;
import controlador.builders.XmlBuilder;
import controlador.common.ResultServlet;
import controlador.common.UserConnectionData;
import controlador.controlResult.EnvironmentStatus;
import controlador.controlResult.GenericStatus;
import controlador.controlResult.OnlineStatus;
import controlador.xmlCheck.CheckXmlOnline;

public class OnlineValidation extends Validation{
	private final String servlet_AM= "/servlet/isf.servlets.gccc.GcccCustomerServlet";
	private final String servlet_MIN= "/servlet/isf.servlets.gcqs.GcqsQuerySystemServlet";
	private final String method_AM= "searchByCustomer";
	private final String method_MIN= "Online_execute";
	private boolean AM;
	private String servlet;
	private String method;
	private UserConnectionData data;
	private ResultServlet returnValue;
	private GenericStatus status;
	public OnlineValidation(UserConnectionData userData, boolean AM){
		data = userData;
		this.AM = AM;
		if(AM){
			servlet = servlet_AM;
			method = method_AM;
		}else{
			servlet = servlet_MIN;
			method = method_MIN;
		}
		status = new OnlineStatus();
	}
	@Override
	public void validate() throws Exception {
		//Crea el objeto que recupera el xml a enviar
		XmlBuilder builder = new XmlBuilder(data, method);
		//Crea el objecto que va a trabajar sobre el servlet
		EnvAccess env = new EnvAccess(data, builder.getXml(),servlet);
		//Realiza el envio al servlet
		returnValue = env.invokeServlet();
		//Valida el resultado devuelto de la prueba
		CheckXmlOnline check = new CheckXmlOnline(returnValue, AM);
		if(check.validarResultado()){
			status.setCurrentStatus(EnvironmentStatus.CURRENT_STATUS_OK);
			status.setErrorMessage("");
			if(status.getElapsedTime()>5000){
				status.setCurrentStatus(EnvironmentStatus.CURRENT_STATUS_REV);
				status.setErrorMessage("El tiempo de respuesta del servidor es lento");
			}
		}else{
			status.setCurrentStatus(EnvironmentStatus.CURRENT_STATUS_KO);
			if(check.getErrorMessage()!=null)
				status.setErrorMessage(check.getErrorMessage());
			else
				status.setErrorMessage(returnValue.getErrorMessage());
		}
	}

	@Override
	public GenericStatus getCurrentStatus() {
		return status;
	}

	@Override
	protected void logActivity() {
		//No realiza accion alguna, el resultado de la validacion se mostraran directamente
	}

}
