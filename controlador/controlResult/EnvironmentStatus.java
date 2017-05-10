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
	
	public EnvironmentStatus(){
		currentStatus = -1;
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

	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
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
}
