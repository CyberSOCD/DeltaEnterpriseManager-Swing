package common;

import drivers.WebClass;

public class ResultServlet {
	
	private String errorMessage = "";
	private long duration;
	private WebClass driver;

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
}