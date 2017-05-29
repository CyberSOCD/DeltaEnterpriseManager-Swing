package controlResult;

import java.awt.Color;

/**
 * 
 * Se encarga de almacenar el estado actual de un entorno
 *
 */
public class EnvironmentStatus implements GenericStatus{
	private int currentStatus;
	private long elapsedTime;
	private String errorMessage = "";
	private final long freqValidation = 500;
	
	public EnvironmentStatus(){
		currentStatus = -1;
	}
	
	@Override
	public int getCurrentStatus() {
		return currentStatus;
	}
	
	@Override
	public void setCurrentStatus(int currentStatus) {
		this.currentStatus = currentStatus;
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
	
	public String getParsedCurrentStatus(int status){
		String value = "";
		if(status == CURRENT_STATUS_KO){
			value = CURRENT_STATUS_KO_STRING;
		}else if(status == CURRENT_STATUS_OK){
			value = CURRENT_STATUS_OK_STRING;
		}else{
			value = CURRENT_STATUS_UNKOWN_STRING;
		}
		return value;
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}
	
	@Override
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	@Override
	public boolean finishValidation() {
		if(currentStatus == CURRENT_STATUS_KO || currentStatus == CURRENT_STATUS_OK||
				currentStatus == CURRENT_STATUS_REV || currentStatus == CURRENT_STATUS_UNKOWN){
			return true;
		}
		return false;
	}
	
	public Color getStatusColor(){
		Color returnColor = GenericStatus.COLOR_STATUS_PDT;
		if(this.currentStatus == GenericStatus.CURRENT_STATUS_OK){
			returnColor = GenericStatus.COLOR_STATUS_OK;
		}else if(this.currentStatus == GenericStatus.CURRENT_STATUS_KO){
			returnColor = GenericStatus.COLOR_STATUS_KO;
		}else if(this.currentStatus == GenericStatus.CURRENT_STATUS_REV){
			returnColor = GenericStatus.COLOR_STATUS_REV;
		}else if(this.currentStatus == GenericStatus.CURRENT_STATUS_PDT){
			returnColor = GenericStatus.COLOR_STATUS_PDT;
		}
		return returnColor;
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
	public long getFrequencyValidation() {
		return freqValidation;
	}
}
