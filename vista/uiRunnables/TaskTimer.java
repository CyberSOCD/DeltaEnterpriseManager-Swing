package uiRunnables;

import uiPanels.Status.GenericStatusPanel;

public class TaskTimer implements Runnable{

	private GenericStatusPanel genericStatusPanel;
	private double minutes = 1;
	public TaskTimer(GenericStatusPanel envPanel, double minute){
		minutes = minute;
		this.genericStatusPanel = envPanel;
	}
	public TaskTimer(GenericStatusPanel envPanel){
		this.genericStatusPanel = envPanel;
	}
	
	public void setTime(int time){
		minutes = time;
	}
	
	@Override
	public void run() {
		while(genericStatusPanel.isTesting()){
			try {
				genericStatusPanel.validateStatus();
				Thread.sleep((long) (minutes * 60 * 1000));
			} catch (InterruptedException e) {
			}
		}
	}
}
