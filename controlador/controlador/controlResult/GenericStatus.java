package controlador.controlResult;

import java.awt.Color;

public interface GenericStatus {
	
	public enum CurrentStatusValue {
		KO, OK, SLOW, REVISION, UNKNOWN
	}

	//Constants
	
	public final static int CURRENT_STATUS_UNKNOWN =-1;
	public final static int CURRENT_STATUS_OK = 0;
	public final static int CURRENT_STATUS_KO = 1;
	public final static int CURRENT_STATUS_PDT = 2;
	public final static int CURRENT_STATUS_REV = 3;
	
	public final static String CURRENT_STATUS_UNKNOWN_STRING ="?";
	public final static String CURRENT_STATUS_OK_STRING = "OK";
	public final static String CURRENT_STATUS_KO_STRING = "KO";
	public final static String CURRENT_STATUS_PDT_STRING = "Pdt.";
	public final static String CURRENT_STATUS_REV_STRING = "Rev.";
	
	public final static Color COLOR_STATUS_UNKW = Color.ORANGE.darker();
	public final static Color COLOR_STATUS_OK = new Color(169,209,142);
	public final static Color COLOR_STATUS_KO = new Color(254,85,72);
	public final static Color COLOR_STATUS_PDT = Color.GRAY.brighter();
	public final static Color COLOR_STATUS_REV = new Color(242,227,134);
	
	/**
	 * Devuelve codigo numerico con el resultado actual
	 * @return
	 */
	public abstract int getCurrentStatus();
	
	public abstract long getElapsedTime();
	
	public abstract String getErrorMessage();
	
	public abstract Color getColor(int status);
	
	public abstract String getStateName();
	
	public abstract boolean finishValidation();
	
	public abstract void setElapsedTime(long elapsedTime);
	
	public abstract void setCurrentStatus(int status);
	
	public abstract void setErrorMessage(String message);
	
	public abstract long getFrequencyValidation();
	
}
