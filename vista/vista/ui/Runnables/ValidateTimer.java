package vista.ui.Runnables;

import vista.ui.Panels.Status.DumbStatusPanel;

public class ValidateTimer implements Runnable{

	private DumbStatusPanel panel;
	private long frequency;
	
	public ValidateTimer(DumbStatusPanel pan, long miliseconds){
		panel = pan;
		frequency = miliseconds;
	}
	
	@Override
	public void run() {
		//Revisa periodicamente que el estado de la validación
		//llegue a un estado "final"
		do{
			panel.validateAsync();
			try {
				Thread.sleep(frequency);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}while(panel.isTesting());
		
	}

}
