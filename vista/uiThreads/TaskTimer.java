package uiThreads;

import uiPanels.Status.StatusPanel;

public class TaskTimer implements Runnable{

	private StatusPanel statusPanel;
	private int minutes = 1;
	public TaskTimer(StatusPanel envPanel, int minute){
		minutes = minute;
		this.statusPanel = envPanel;
	}
	
	public void setTime(int time){
		minutes = time;
	}
	
	@Override
	public void run() {
//		System.out.println("Se iniciliza la acción del hilo");
		
		while(statusPanel.isTesting()){
			try {
//				System.out.println("---- Se realiza la validación");
				statusPanel.validateStatus();
//				System.out.println("---- Validación finalizada");
				Thread.sleep(minutes * 60 * 1000);
			} catch (InterruptedException e) {
//				e.printStackTrace();
			}
		}
	}
}
