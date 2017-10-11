package controlador.controlResult;

import java.awt.Color;

public class BatchStatus implements GenericStatus{

	@Override
	public int getCurrentStatus() {
		return 0;
	}

	@Override
	public void setCurrentStatus(int status) {
		
	}

	@Override
	public long getElapsedTime() {
		return 0;
	}

	@Override
	public void setElapsedTime(long elapsedTime) {
		
	}

	@Override
	public String getErrorMessage() {
		return null;
	}

	@Override
	public void setErrorMessage(String message) {
		
	}

	@Override
	public Color getColor(int status) {
		return null;
	}

	@Override
	public boolean finishValidation() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getStateName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getFrequencyValidation() {
		// TODO Auto-generated method stub
		return 0;
	}

}
