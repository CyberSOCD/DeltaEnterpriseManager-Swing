package uiPanels.Status;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import common.UserConnectionData;
import controlResult.GenericStatus;
import uiRunnables.TaskTimer;
import uiRunnables.ValidateTimer;
import validationPackage.Validation;

/**
 * 
 * Panel básico que muestra estados concretos de
 * cualquier validación con la que se haya incializado
 *
 */
@SuppressWarnings("serial")
public class DumbStatusPanel extends JPanel implements GenericStatusPanel{
	private UserConnectionData data;
	private JLabel state;
	private Validation validation;
	private GenericStatus status;
	private JPanel mainPanel;
	private ValidateTimer timer = null;
	private boolean testing = false;
//	private boolean async = false;
	private boolean first = true;
	private long sleepTime;
	
//	public DumbStatusPanel(Validation validation, GenericStatus status, UserConnectionData data){
//		this.data = data;
//		this.validation = validation;
//		this.status = status;
//		initalize();
//	}
	/**
	 * La validacion se realiza periodicamente hasta alcanzar un estado final
	 * el tiempo en minutos viene por el parametro sleep
	 * @param validation
	 * @param status
	 * @param data
	 * @param sleep
	 */
	public DumbStatusPanel(Validation validation, GenericStatus status, UserConnectionData data){
		this.data = data;
		this.validation = validation;
		this.status = status;
		timer = new ValidateTimer(this,status.getFrequencyValidation());
		sleepTime = status.getFrequencyValidation();
//		async = true;
		initalize();
	}
	
	public boolean isTesting(){
		return testing;
	}
	
	/**
	 * No se permite relanzar validación habiendo una en curso 
	 * para evitar saturar las peticiones de validacion
	 */
	@Override
	public void validateStatus() {
		//En caso de que haya una prueba en vuelo no se vuelve a validar
		if(!isTesting()){
			testing = true;
			first = true;
			new Thread(timer).start();
		}
	}
	
	public void validateAsync(){
		System.out.println("Entra en Async --- ");
		if(first){
			try {
				validation.validate();
			} catch (Exception e) {
				e.printStackTrace();
			}
			first = false;
		}
		status = validation.getCurrentStatus();
		updateComponents();
		testing = !status.finishValidation();
	}
	
	public void resetState(){
		state.setText(GenericStatus.CURRENT_STATUS_PDT_STRING);
		mainPanel.setBackground(GenericStatus.COLOR_STATUS_PDT);
		for(Component c:mainPanel.getComponents()){
			if(c instanceof JLabel){
				mainPanel.remove(c);
			}
		}
		mainPanel.add(state);
	}
	
	public UserConnectionData getUserData(){
		return data;
	}
	
	private void initalize(){
		this.setLayout(new BorderLayout());
		mainPanel = new JPanel();
		//Se inicializa componente dando estado inicial PENDIENTE
		state = new JLabel();
		state.setText(GenericStatus.CURRENT_STATUS_PDT_STRING);
		state.setAlignmentX(CENTER_ALIGNMENT);
		state.setAlignmentY(CENTER_ALIGNMENT);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(state);
		mainPanel.setBackground(GenericStatus.COLOR_STATUS_PDT);
		mainPanel.setPreferredSize(new Dimension(200, 70));
		mainPanel.setMaximumSize(new Dimension(200, 70));
		mainPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.setAlignmentY(CENTER_ALIGNMENT);
		this.setBackground(Color.LIGHT_GRAY);
		this.add(mainPanel,BorderLayout.CENTER);
	}
	
	private void updateComponents(){
		Color c = status.getColor(status.getCurrentStatus());
		String statusName = status.getStateName();
		state.setText(statusName);
		checkErrorMessage();
		mainPanel.setBackground(c);
		validate();
		repaint();
	}
	
	/**
	 * Añade mensaje de error en el panel o lo elimina 
	 * en funcion del estado de la validacion
	 */
	private void checkErrorMessage(){
		//Se eliminan JLabels actuales
		for(Component c:mainPanel.getComponents()){
			if(c instanceof JLabel){
				mainPanel.remove(c);
			}
		}
		mainPanel.add(state);
		if(status.getCurrentStatus() == GenericStatus.CURRENT_STATUS_KO){
			setError(status.getErrorMessage());
		}
		state.setAlignmentX(CENTER_ALIGNMENT);
		state.setAlignmentY(CENTER_ALIGNMENT);
	}
	
	private void setError(String message){
		//Se dejan 5 palabras por label para evitar redimensionar
		JLabel label;
		int cont = 0;
		String parcialMsg = "";
		for(String word:message.split(" ")){
			if(cont==3){
				parcialMsg = parcialMsg + " " + word;
				label = new JLabel(parcialMsg);
				label.setFont(new Font("Arial", Font.PLAIN, 10));
				mainPanel.add(label);
				label.setAlignmentX(CENTER_ALIGNMENT);
				parcialMsg = "";
				cont = -1;
			}else{
				parcialMsg = parcialMsg + " " + word;
			}
			cont++;
		}
		if(cont<=3){
			label = new JLabel(parcialMsg);
			label.setFont(new Font("Arial", Font.PLAIN, 10));
			mainPanel.add(label);
			label.setAlignmentX(CENTER_ALIGNMENT);
		}
	}
}
