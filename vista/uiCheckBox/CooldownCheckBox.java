package uiCheckBox;

import javax.swing.JCheckBox;

import uiRunnables.CheckBoxTimer;

@SuppressWarnings("serial")

/**
 * 
 * El componente permite configurar un tiempo de inhabilitación
 * para que quede deshabilitado por un tiempo configurable
 *
 */
public class CooldownCheckBox extends JCheckBox{
	private long time;
	
	public CooldownCheckBox(long miliseconds){
		super();
		time = miliseconds;
	}
	
	/**
	 * Se des selecciona el componente e inhabilita por el tiempo definido 
	 */
	public void activate(){
		this.setSelected(false);
		this.setEnabled(false);
		CheckBoxTimer c = new CheckBoxTimer(this, time);
		new Thread(c,"Hilo Huerfano").start();
	}
}
