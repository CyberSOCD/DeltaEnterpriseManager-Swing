package uiRunnables;

import uiCheckBox.CooldownCheckBox;
import uiPanels.Status.DumbStatusPanel;

public class CheckBoxTimer implements Runnable{
	private CooldownCheckBox check;
	private long time;
	private DumbStatusPanel[] panels;
	
	public CheckBoxTimer(CooldownCheckBox check, long time, DumbStatusPanel[] panels){
		this.check = check;
		this.time = time;
		this.panels = panels;
	}
	
	@Override
	public void run() {
		try{
			//Reliza la espera por defecto inicialmente
			startThreadAction();
			//Comprueba en ciclos de 2 segundos que todos los paneles han finalizado
			while(!finishValidation()){
				Thread.sleep(1000);
			}
		}catch(InterruptedException e){
			e.printStackTrace();
		}finally{
		}
		check.setEnabled(true);
	}
	
	private void startThreadAction() throws InterruptedException{
		Thread.sleep(time);
	}
	
	private boolean finishValidation(){
		for(DumbStatusPanel panel:panels){
			if(panel.isTesting()){
				return false;
			}
		}
		return true;
	}
}
