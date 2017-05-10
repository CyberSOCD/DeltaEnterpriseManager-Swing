package validationPackage;

import common.UserConnectionData;

import controlResult.GenericStatus;
import controlResult.OfflineStatus;

public class OfflineValidation extends Validation{
	
	private UserConnectionData data;
	private OfflineStatus status;
	private int idProcces;
	
	public OfflineValidation(UserConnectionData userData){
		data = userData;
		status = new OfflineStatus();
	}

	@Override
	public void validate() throws Exception {
		//Lanzar tarea Offline Invocando Servlet
		
		//Recuperar de la respuesta la idProcess
		
		//Consultar a la BBDD para obtener un estado inicial de la tarea
		refreshStatus();
	}
	
	/**
	 * Revisa de nuevo el estado de la tarea offline
	 */
	public void refreshStatus(){
		
	}

	@Override
	public GenericStatus getCurrentStatus() {
		return null;
	}

	@Override
	protected void logActivity() {
		
	}

}
