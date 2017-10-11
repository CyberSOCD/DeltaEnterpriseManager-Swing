package controlador.validationPackage;

import conexion.dbAccess.OfflineQueries;
import conexion.drivers.DriverDB;
import conexion.envAccess.EnvAccess;
import controlador.builders.XmlBuilder;
import controlador.common.ResultServlet;
import controlador.common.UserConnectionData;
import controlador.controlResult.GenericStatus;
import controlador.controlResult.OfflineStatus;
import controlador.xmlCheck.CheckXmlOffline;

public class OfflineValidation extends Validation{
	
	private UserConnectionData data;
	private OfflineStatus status;
	private ResultServlet returnValue;
	private String idProcces;
	private String method;
	private String servlet = "/servlet/isf.servlets.gccom.report.ReportGeneratorServlet";
	private DriverDB db;
	private int countQueuedState = 0;
	
	public OfflineValidation(UserConnectionData userData){
		data = userData;
		status = new OfflineStatus();
		method = "offline_validation_MIN";
	}

	@Override
	public void validate() throws Exception {
		countQueuedState = 0;
		XmlBuilder builder = new XmlBuilder(data, method);
		//Lanzar tarea Offline Invocando Servlet
		EnvAccess env = new EnvAccess(data,builder.getXml(),servlet);
		returnValue = env.invokeServlet();
		if(returnValue.getErrorCode()>0){
			//Se trata como error del servidor no se continua con el resto de validaciones
			System.out.println("Entra en el error");
			status.setCurrentStatusServer(1);
			status.setErrorMessage(returnValue.getErrorMessage());
		}else{
//			System.out.println("No entra en el error " + returnValue);
			//Recuperar de la respuesta la idProcess
			CheckXmlOffline check = new CheckXmlOffline(returnValue);
			idProcces = check.getIdProccess();
			status.setCurrentStatus(Integer.valueOf(idProcces));
			if(check.respuestaCorrecta()){
				db = new DriverDB(data, OfflineQueries.getOfflineStatus_FromIdProcess(idProcces));
				status.setCurrentStatus(Integer.valueOf(db.getFirstRowColumnNoRaiseExcp("TASK_STATUS_ID")));
			}
		}
		//Consultar a la BBDD para obtener un estado inicial de la tarea
	}
	
	@Override
	public GenericStatus getCurrentStatus(){
		if(returnValue.getErrorCode()>0){
			//Se trata como error del servidor no se continua con el resto de validaciones
			status.setCurrentStatusServer(1);
			status.setErrorMessage(returnValue.getErrorMessage());
		}else{
			if(status.getCurrentStatus() == OfflineStatus.CURRENT_STATUS_OFF_QUEUED){
				countQueuedState++;
				status.setCurrentStatus(Integer.valueOf(db.getFirstRowColumnNoRaiseExcp("TASK_STATUS_ID")),countQueuedState);
			}else{
				status.setCurrentStatus(Integer.valueOf(db.getFirstRowColumnNoRaiseExcp("TASK_STATUS_ID")));
			}
		}
		return status;
	}

	@Override
	protected void logActivity() {
		
	}

}
