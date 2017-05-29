package uiRunnables;

import uiCheckBox.CooldownCheckBox;

public class CheckBoxTimer implements Runnable{
	private CooldownCheckBox check;
	private long time;
	
	public CheckBoxTimer(CooldownCheckBox check, long time){
		this.check = check;
		this.time = time;
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(time);
			check.setEnabled(true);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
