package controlador.controlResult;

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
	public final static int CURRENT_STATUS_OFF_QUEUED_KO = 14;
	
	public final static String CURRENT_STATUS_OFF_QUEUED_STRING = "En cola";
	public final static String CURRENT_STATUS_OFF_RUNNING_STRING = "En curso";
	public final static String CURRENT_STATUS_OFF_INTERRUPTED_STRING = "Interrumpida";
	public final static String CURRENT_STATUS_OFF_PAUSED_STRING = "Pausada";
	public final static String CURRENT_STATUS_OFF_ENDED_OK_STRING = GenericStatus.CURRENT_STATUS_OK_STRING;
	public final static String CURRENT_STATUS_OFF_ENDED_KO_STRING = GenericStatus.CURRENT_STATUS_KO_STRING;
	public final static String CURRENT_STATUS_OFF_ENDED_ABR_USR_STRING = "Interrumpida por usuario";
	public final static String CURRENT_STATUS_OFF_ENDED_TIMEOUT_STRING = "Interrumpida por timeout";
	public final static String CURRENT_STATUS_OFF_SCHEDULED_STRING = "Programada";
	public final static String CURRENT_STATUS_OFF_STARTED_STRING = "Iniciada";
	public final static String CURRENT_STATUS_OFF_QUEUED_KO_STRING = "No procesa las tareas";
	
	public final static Color COLOR_STATUS_OFF_QUEUED = new Color(199,221,241);
	public final static Color COLOR_STATUS_OFF_RUNNING = COLOR_STATUS_OFF_QUEUED;
	public final static Color COLOR_STATUS_OFF_INTERRUPTED = GenericStatus.COLOR_STATUS_KO;
	public final static Color COLOR_STATUS_OFF_PAUSED = GenericStatus.COLOR_STATUS_PDT;
	public final static Color COLOR_STATUS_OFF_ENDED_OK = GenericStatus.COLOR_STATUS_OK;
	public final static Color COLOR_STATUS_OFF_ENDED_KO = GenericStatus.COLOR_STATUS_KO;
	public final static Color COLOR_STATUS_OFF_ENDED_ABR_USR = GenericStatus.COLOR_STATUS_KO;
	public final static Color COLOR_STATUS_OFF_ENDED_TIMEOUT = GenericStatus.COLOR_STATUS_KO;
	public final static Color COLOR_STATUS_OFF_SCHEDULED = GenericStatus.COLOR_STATUS_PDT;
	public final static Color COLOR_STATUS_OFF_STARTED = COLOR_STATUS_OFF_QUEUED;
	public final static Color COLOR_STATUS_OFF_QUEUED_KO = GenericStatus.COLOR_STATUS_KO;
	
	private int currentStatus;
	private String currentError;
	private long elapsedTime;
	private int timeQueued = 0;
	private final long freqValidation = 5000;
	private final long timeoutQueued = 20000;

	@Override
	public int getCurrentStatus() {
		return currentStatus;
	}
	
	/**
	 * Recibe un codigo de la BBDD del estado de la tarea offline
	 * y se traduce en los codigos de Status definidos
	 */
	@Override
	public void setCurrentStatus(int offlineCode) {
		currentStatus = getStatusFromOfflineCode(offlineCode);
	}
	
	/**
	 * Sobrecarga controla el tiempo maximo de tarea encolada 
	 * hasta que lo consideremos KO
	 * @param offlineCode
	 * @param countQueuedState
	 */
	public void setCurrentStatus(int offlineCode,int countQueuedState) {
		timeQueued = (int) ((countQueuedState * freqValidation)/5);
		if(timeQueued >= timeoutQueued){
			timeQueued = 0;
			setCurrentStatus(-15);
		}else{
			setCurrentStatus(offlineCode);
		}
	}
	
	public void setCurrentStatusServer(int errorCode){
		currentStatus = errorCode;
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
		currentError = message;
	}
	
	@Override
	public boolean finishValidation() {
		if(currentStatus == CURRENT_STATUS_OFF_ENDED_ABR_USR ||currentStatus ==CURRENT_STATUS_KO
				|| currentStatus ==CURRENT_STATUS_OFF_ENDED_KO || currentStatus ==CURRENT_STATUS_OFF_ENDED_OK
				|| currentStatus ==CURRENT_STATUS_OFF_ENDED_TIMEOUT || currentStatus ==CURRENT_STATUS_OFF_INTERRUPTED
				|| currentStatus ==CURRENT_STATUS_REV || currentStatus ==CURRENT_STATUS_UNKOWN
				|| currentStatus == CURRENT_STATUS_OFF_QUEUED_KO){
			timeQueued = 0;
			return true;
		}
		return false;
	}
	
	@Override
	public String getStateName() {
		String message = "";
		switch(currentStatus){
		case CURRENT_STATUS_KO:
			message= CURRENT_STATUS_KO_STRING;
			break;
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
		case CURRENT_STATUS_OFF_QUEUED_KO:
			message= CURRENT_STATUS_OFF_QUEUED_KO_STRING;
			break;
		default:
			message = GenericStatus.CURRENT_STATUS_UNKOWN_STRING;
			break;
		}
		return message;
	}

	@Override
	public Color getColor(int status) {
		Color color;
		switch(currentStatus){
		case CURRENT_STATUS_KO:
			color= COLOR_STATUS_KO;
			break;
		case CURRENT_STATUS_OFF_ENDED_OK:
			color= COLOR_STATUS_OFF_ENDED_OK;
			break;
		case CURRENT_STATUS_OFF_ENDED_KO:
			color= COLOR_STATUS_OFF_ENDED_KO;
			break;
		case CURRENT_STATUS_OFF_ENDED_ABR_USR:
			color= COLOR_STATUS_OFF_ENDED_ABR_USR;
			break;
		case CURRENT_STATUS_OFF_ENDED_TIMEOUT:
			color= COLOR_STATUS_OFF_ENDED_TIMEOUT;
			break;
		case CURRENT_STATUS_OFF_INTERRUPTED:
			color= COLOR_STATUS_OFF_INTERRUPTED;
			break;
		case CURRENT_STATUS_OFF_PAUSED:
			color= COLOR_STATUS_OFF_PAUSED;
			break;
		case CURRENT_STATUS_OFF_QUEUED:
			color= COLOR_STATUS_OFF_QUEUED;
			break;
		case CURRENT_STATUS_OFF_RUNNING:
			color= COLOR_STATUS_OFF_RUNNING;
			break;
		case CURRENT_STATUS_OFF_SCHEDULED:
			color= COLOR_STATUS_OFF_SCHEDULED;
			break;
		case CURRENT_STATUS_OFF_STARTED:
			color= COLOR_STATUS_OFF_STARTED;
			break;
		case CURRENT_STATUS_OFF_QUEUED_KO:
			color= COLOR_STATUS_OFF_QUEUED_KO;
			break;
		default:
			color = GenericStatus.COLOR_STATUS_UNKW;
			break;
		}
		return color;
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
		case -15:
			returnValue = CURRENT_STATUS_OFF_QUEUED_KO;
			break;
		default:
			returnValue = GenericStatus.CURRENT_STATUS_UNKOWN;
			break;
		}
		return returnValue;
	}

	@Override
	public long getFrequencyValidation() {
		return freqValidation;
	}
}
