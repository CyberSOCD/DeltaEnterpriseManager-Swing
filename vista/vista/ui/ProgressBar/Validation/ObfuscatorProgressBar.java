package vista.ui.ProgressBar.Validation;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.File;

import javax.swing.JProgressBar;

import controlador.common.UserConnectionData;
import controlador.objects.obfuscator.ObfuscatorObject;
import controlador.validationPackage.ObfuscatorValidation;

@SuppressWarnings("serial")
public class ObfuscatorProgressBar extends JProgressBar implements ProgressBarValidation{

	private ObfuscatorValidation validator;
	private UserConnectionData data;
	private File file;
	private int maxTables;
	private int incr = 1;
	private int currentProgression = 0;
	private Color color = Color.GREEN.darker();
	public ObfuscatorProgressBar(){
		super();
		setStringPainted(true);
		setForeground(color);
		setMinimum(0);
	}
	
	public void setUserConnectionData(UserConnectionData data){
		this.data = data;
	}
	
	public ObfuscatorObject getValidationResult(){
		return validator.getObject();
	}
	
	/**
	 * Sets the File of the progressBar
	 * @param file
	 */
	public void setFile(File file){
		this.file = file;
	}

	@Override
	public void setMaxBar(int max) {
		this.maxTables = max;
		setMaximum(max);
	}

	@Override
	public void setIncrProgression(int incr) {
		this.incr = incr;
	}

	@Override
	public void increaseProgression() {
		currentProgression =  currentProgression + incr;
		double percent = (currentProgression*100)/maxTables;
		setString(Double.toString(percent) + "%");
		this.setValue(currentProgression);
	}
	
	@Override
	public void setToolTip(String table){
		setToolTipText(table);
	}

	@Override
	public void resetBar() {
		setVisible(false);
		currentProgression = 0;
		setString("0%");
	}

	@Override
	public boolean isTesting() {
		return currentProgression < maxTables;
	}

	@Override
	public void startValidation() {
		try {
			validator = new ObfuscatorValidation(file, data,this);
			validator.validate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
