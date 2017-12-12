package vista.ui.ProgressBar.Validation;

public abstract interface ProgressBarValidation {
	
	public abstract void startValidation();
	
	public abstract boolean isTesting();

	public abstract void setMaxBar(int max);
	
	public abstract void setIncrProgression(int incr);
	
	public abstract void increaseProgression();
	
	public abstract void resetBar();

	public void setToolTip(String table);
}
