package vista.ui.Runnables;

import vista.ui.Panels.ObfuscatorPanel;
import vista.ui.ProgressBar.Validation.ObfuscatorProgressBar;
import vista.ui.ProgressBar.Validation.ProgressBarValidation;

public class JProgressBarTask implements Runnable{

	private ProgressBarValidation progress;
	private ObfuscatorPanel panel;
	
	public JProgressBarTask(ProgressBarValidation val, ObfuscatorPanel panel){
		this.panel = panel;
		progress = val;
	}
	@Override
	public void run() {
		progress.startValidation();
		while(progress.isTesting()){}
		panel.finishValidation(((ObfuscatorProgressBar)progress).getValidationResult());
	}

}
