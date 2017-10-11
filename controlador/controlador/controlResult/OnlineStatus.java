package controlador.controlResult;

import java.awt.Color;

public class OnlineStatus implements GenericStatus{
	
	private int currentStatus;
	private long elapsedTime;
	private String errorMessage;
	private final long freqValidation = 500;
	
	public OnlineStatus(){
		
	}
	
	@Override
	public long getFrequencyValidation() {
		return freqValidation;
	}

	public int getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(int currentStatus) {
		this.currentStatus = currentStatus;
	}

	public long getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	@Override
	public Color getColor(int status) {
		Color color;
		switch(currentStatus){
		case CURRENT_STATUS_OK:
			color= COLOR_STATUS_OK;
			break;
		case CURRENT_STATUS_KO:
			color= COLOR_STATUS_KO;
			break;
		case CURRENT_STATUS_PDT:
			color= COLOR_STATUS_PDT;
			break;
		case CURRENT_STATUS_REV:
			color= COLOR_STATUS_REV;
			break;
		default:
			color = COLOR_STATUS_UNKW;
			break;
		}
		return color;
	}

	@Override
	public boolean finishValidation() {
		if(currentStatus == CURRENT_STATUS_KO || currentStatus == CURRENT_STATUS_OK||
				currentStatus == CURRENT_STATUS_REV || currentStatus == CURRENT_STATUS_UNKOWN){
			return true;
		}
		return false;
	}
	
	@Override
	public String getStateName() {
		String result;
		switch(currentStatus){
		case CURRENT_STATUS_OK:
			result = CURRENT_STATUS_OK_STRING;
			break;
		case CURRENT_STATUS_KO:
			result = CURRENT_STATUS_KO_STRING;
			break;
		case CURRENT_STATUS_PDT:
			result = CURRENT_STATUS_PDT_STRING;
			break;
		case CURRENT_STATUS_REV:
			result = CURRENT_STATUS_REV_STRING;
			break;
		default:
			result  = CURRENT_STATUS_UNKOWN_STRING;
			break;
		}
		return result;
	}
}
