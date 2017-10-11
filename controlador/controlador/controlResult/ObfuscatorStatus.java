package controlador.controlResult;

import java.awt.Color;

public class ObfuscatorStatus implements GenericStatus {

	@Override
	public int getCurrentStatus() {
		return 0;
	}

	@Override
	public long getElapsedTime() {
		return 0;
	}

	@Override
	public String getErrorMessage() {
		return null;
	}

	@Override
	public Color getColor(int status) {
		return null;
	}

	@Override
	public String getStateName() {
		return null;
	}

	@Override
	public boolean finishValidation() {
		return false;
	}

	@Override
	public void setElapsedTime(long elapsedTime) {

	}

	@Override
	public void setCurrentStatus(int status) {

	}

	@Override
	public void setErrorMessage(String message) {

	}

	@Override
	public long getFrequencyValidation() {
		return 0;
	}

}
