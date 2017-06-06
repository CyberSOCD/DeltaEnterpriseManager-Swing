package uiCheckBox;

import java.util.HashMap;

import javax.swing.JCheckBox;

import uiPanels.Status.DumbStatusPanel;
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
	 * pudiendose incrementar hasta que finalicen todas las validaciones
	 * pendientes
	 */
	public void activate(HashMap<CooldownCheckBox,DumbStatusPanel[]> relation){
		this.setSelected(false);
		this.setEnabled(false);
		CheckBoxTimer c = new CheckBoxTimer(this, time, relation.get(this));
		new Thread(c).start();
		
	}
}
