package dbAccess;

public class OfflineQueries {

	private static String OFFLINE_STATE_FROM_ID_PROCESS = "SELECT TASK_STATUS_ID FROM DELTA_ARQ_ADMINIS.GCOFF_TASK WHERE TASK_ID = ";
	public OfflineQueries(){
		
	}
	
	public static String getOfflineStatus_FromIdProcess(String idProcess){
		return OFFLINE_STATE_FROM_ID_PROCESS + idProcess;
	}
	
}
