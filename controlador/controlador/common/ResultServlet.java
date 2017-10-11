package controlador.common;

import conexion.drivers.WebClass;

public class ResultServlet {
	
	private String errorMessage = "";
	private long duration;
	private WebClass driver;
	private int errorCode = 0;

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public WebClass getDriver() {
		return driver;
	}

	public void setDriver(WebClass driver) {
		this.driver = driver;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}