package uiPanels.Status;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.net.MalformedURLException;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import common.UserConnectionData;
import controlResult.GenericStatus;
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
	
	public DumbStatusPanel(Validation validation, GenericStatus status, UserConnectionData data){
		this.data = data;
		this.validation = validation;
		this.status = status;
		initalize();
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
	
	@Override
	public void validateStatus() {
		try{
			validation.validate();
			status = validation.getCurrentStatus();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		updateComponents();
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
		return data ;
	}
	
	private void updateComponents(){
		Color c;
		String value;
		switch(status.getCurrentStatus()){
			case GenericStatus.CURRENT_STATUS_KO:
				c = GenericStatus.COLOR_STATUS_KO;
				value = GenericStatus.CURRENT_STATUS_KO_STRING;
				break;
			case GenericStatus.CURRENT_STATUS_OK:
				c = GenericStatus.COLOR_STATUS_OK;
				value = GenericStatus.CURRENT_STATUS_OK_STRING;
				break;
			case GenericStatus.CURRENT_STATUS_PDT:
				c = GenericStatus.COLOR_STATUS_PDT;
				value = GenericStatus.CURRENT_STATUS_PDT_STRING;
				break;
			case GenericStatus.CURRENT_STATUS_REV:
				c = GenericStatus.COLOR_STATUS_REV;
				value = GenericStatus.CURRENT_STATUS_REV_STRING;
				break;
			case GenericStatus.CURRENT_STATUS_UNKOWN:
				c = GenericStatus.COLOR_STATUS_UNKW;
				value = GenericStatus.CURRENT_STATUS_UNKOWN_STRING;
				break;
			default:
				c = GenericStatus.COLOR_STATUS_UNKW;
				value = GenericStatus.CURRENT_STATUS_UNKOWN_STRING;
				break;
		}
		state.setText(value);
		checkErrorMessage();
		mainPanel.setBackground(c);
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
		//Se dejan 4 palabras por label para evitar redimensionar
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
