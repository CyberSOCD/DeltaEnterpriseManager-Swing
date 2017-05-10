package controlResult;

import java.awt.Color;

public class OfflineStatus implements GenericStatus{
	
	public final static int CURRENT_STATUS_OFF_QUEUED = 4;
	public final static int CURRENT_STATUS_OFF_RUNNING = 5;
	public final static int CURRENT_STATUS_OFF_INTERRUPTED = 6;
	public final static int CURRENT_STATUS_OFF_PAUSED = 7;
	public final static int CURRENT_STATUS_OFF_ENDED_OK = 8;
	public final static int CURRENT_STATUS_OFF_ENDED_KO = 9;
	public final static int CURRENT_STATUS_OFF_ENDED_ABR_USR = 10;
	public final static int CURRENT_STATUS_OFF_ENDED_TIMEOUT = 11;
	public final static int CURRENT_STATUS_OFF_SCHEDULED = 12;
	public final static int CURRENT_STATUS_OFF_STARTED = 13;
	
	public final static String CURRENT_STATUS_OFF_QUEUED_STRING = "Encolada";
	public final static String CURRENT_STATUS_OFF_RUNNING_STRING = "En curso";
	public final static String CURRENT_STATUS_OFF_INTERRUPTED_STRING = "Interrumpida";
	public final static String CURRENT_STATUS_OFF_PAUSED_STRING = "Pausada";
	public final static String CURRENT_STATUS_OFF_ENDED_OK_STRING = GenericStatus.CURRENT_STATUS_OK_STRING;
	public final static String CURRENT_STATUS_OFF_ENDED_KO_STRING = GenericStatus.CURRENT_STATUS_KO_STRING;
	public final static String CURRENT_STATUS_OFF_ENDED_ABR_USR_STRING = "Interrumpida por usuario";
	public final static String CURRENT_STATUS_OFF_ENDED_TIMEOUT_STRING = "Interrumpida por timeout";
	public final static String CURRENT_STATUS_OFF_SCHEDULED_STRING = "Programada";
	public final static String CURRENT_STATUS_OFF_STARTED_STRING = "Iniciada";
	
	public final static Color COLOR_STATUS_OFF_QUEUED = GenericStatus.COLOR_STATUS_PDT;
	public final static Color COLOR_STATUS_OFF_RUNNING = GenericStatus.COLOR_STATUS_PDT;
	public final static Color COLOR_STATUS_OFF_INTERRUPTED = GenericStatus.COLOR_STATUS_KO;
	public final static Color COLOR_STATUS_OFF_PAUSED = GenericStatus.COLOR_STATUS_PDT;
	public final static Color COLOR_STATUS_OFF_ENDED_OK = GenericStatus.COLOR_STATUS_OK;
	public final static Color COLOR_STATUS_OFF_ENDED_KO = GenericStatus.COLOR_STATUS_KO;
	public final static Color COLOR_STATUS_OFF_ENDED_ABR_USR = GenericStatus.COLOR_STATUS_KO;
	public final static Color COLOR_STATUS_OFF_ENDED_TIMEOUT = GenericStatus.COLOR_STATUS_KO;
	public final static Color COLOR_STATUS_OFF_SCHEDULED = GenericStatus.COLOR_STATUS_PDT;
	public final static Color COLOR_STATUS_OFF_STARTED = GenericStatus.COLOR_STATUS_PDT;
	
	public int currentStatus;
	public String currentError;
	public long elapsedTime;

	@Override
	public int getCurrentStatus() {
		return currentStatus;
	}
	
	@Override
	public void setCurrentStatus(int offlineCode) {
		currentStatus = getStatusFromOfflineCode(offlineCode);
	}

	@Override
	public long getElapsedTime() {
		return elapsedTime;
	}

	@Override
	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	@Override
	public String getErrorMessage() {
		return currentError;
	}

	@Override
	public void setErrorMessage(String message) {
		currentError = getError();
	}
	
	private int getStatusFromOfflineCode(int offlineCode){
		int returnValue;
		switch(offlineCode){
		case 10:
			returnValue = CURRENT_STATUS_OFF_RUNNING;
			break;
		case 20:
			returnValue = CURRENT_STATUS_OFF_INTERRUPTED;
			break;
		case 30:
			returnValue = CURRENT_STATUS_OFF_PAUSED;
			break;
		case 40:
			returnValue = CURRENT_STATUS_OFF_ENDED_OK;
			break;
		case 50:
			returnValue = CURRENT_STATUS_OFF_ENDED_KO;
			break;
		case 60:
			returnValue = CURRENT_STATUS_OFF_ENDED_ABR_USR;
			break;
		case 70:
			returnValue = CURRENT_STATUS_OFF_ENDED_TIMEOUT;
			break;
		case 80:
			returnValue = CURRENT_STATUS_OFF_QUEUED;
			break;
		case 1:
			returnValue = CURRENT_STATUS_OFF_SCHEDULED;
			break;
		case 2:
			returnValue = CURRENT_STATUS_OFF_STARTED;
			break;
		default:
			returnValue = GenericStatus.CURRENT_STATUS_UNKOWN;
			break;
		}
		return returnValue;
	}
	
	private String getError(){
		String message = "";
		switch(currentStatus){
		case CURRENT_STATUS_OFF_ENDED_OK:
			message= CURRENT_STATUS_OFF_ENDED_OK_STRING;
			break;
		case CURRENT_STATUS_OFF_ENDED_KO:
			message= CURRENT_STATUS_OFF_ENDED_KO_STRING;
			break;
		case CURRENT_STATUS_OFF_ENDED_ABR_USR:
			message= CURRENT_STATUS_OFF_ENDED_ABR_USR_STRING;
			break;
		case CURRENT_STATUS_OFF_ENDED_TIMEOUT:
			message= CURRENT_STATUS_OFF_ENDED_TIMEOUT_STRING;
			break;
		case CURRENT_STATUS_OFF_INTERRUPTED:
			message= CURRENT_STATUS_OFF_INTERRUPTED_STRING;
			break;
		case CURRENT_STATUS_OFF_PAUSED:
			message= CURRENT_STATUS_OFF_PAUSED_STRING;
			break;
		case CURRENT_STATUS_OFF_QUEUED:
			message= CURRENT_STATUS_OFF_QUEUED_STRING;
			break;
		case CURRENT_STATUS_OFF_RUNNING:
			message= CURRENT_STATUS_OFF_RUNNING_STRING;
			break;
		case CURRENT_STATUS_OFF_SCHEDULED:
			message= CURRENT_STATUS_OFF_SCHEDULED_STRING;
			break;
		case CURRENT_STATUS_OFF_STARTED:
			message= CURRENT_STATUS_OFF_STARTED_STRING;
			break;
		default:
			message = GenericStatus.CURRENT_STATUS_UNKOWN_STRING;
			break;
		}
		return message;
	}

}
