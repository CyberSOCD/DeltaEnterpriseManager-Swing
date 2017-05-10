package uiPanels;

import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

@SuppressWarnings("serial")
public class LoginPanel extends JPanel{
	//Constantes
	private final String profMIN = "Minorista";
	private final String profAM = "Mayorista";
	private final String profAdm = "Administrador";
	//Contenedor principal
	private JPanel mainContainer;

	private JPanel elementsContainer;
	private JPanel buttonsContainer;
	private JPanel labelsContainer;
	private JPanel inputContainer;
	//Labels
	private JLabel labelProfile;
	private JLabel labelPassword;
	//Buttons
	private JButton buttonAccept;
	private JButton buttonCancel;
	//Input Elements
	private JComboBox<String> profileCombo;
	private JPasswordField passwordText;
	
	public LoginPanel(){
		initialize();
	}
	
	private void initialize(){
		
		//Containers
		elementsContainer = new JPanel();
		
		mainContainer = new JPanel();
		add(mainContainer);
		mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
		
		elementsContainer.setLayout(new BoxLayout(elementsContainer, BoxLayout.X_AXIS));
		
		labelsContainer = new JPanel();
		labelsContainer.setLayout(new BoxLayout(labelsContainer, BoxLayout.Y_AXIS));
		labelsContainer.setAlignmentX(LEFT_ALIGNMENT);
		
		inputContainer = new JPanel();
		inputContainer.setLayout(new BoxLayout(inputContainer, BoxLayout.Y_AXIS));
		inputContainer.setAlignmentX(LEFT_ALIGNMENT);
		
		buttonsContainer = new JPanel();
		buttonsContainer.setLayout(new BoxLayout(buttonsContainer, BoxLayout.X_AXIS));
		buttonsContainer.setAlignmentX(LEFT_ALIGNMENT);

		//Labels
		labelProfile = new JLabel();
		labelProfile.setText("Perfil");
		labelProfile.setAlignmentX(LEFT_ALIGNMENT);
		
		
		labelPassword = new JLabel();
		labelPassword.setText("Contraseña");
		labelPassword.setAlignmentX(LEFT_ALIGNMENT);
		
		//Input
		profileCombo = new JComboBox<String>();
		profileCombo.addItem(profMIN);
		profileCombo.addItem(profAM);
		profileCombo.addItem(profAdm);
		
		passwordText = new JPasswordField(10);
		
		//Buttons
		buttonAccept = new JButton("Aceptar");
		
		buttonCancel = new JButton("Cancelar");
		
		buttonsContainer.add(buttonCancel);
		buttonsContainer.add(Box.createRigidArea(new Dimension(30,0)));
		buttonsContainer.add(buttonAccept);
		inputContainer.add(profileCombo);
		inputContainer.add(Box.createRigidArea(new Dimension(0,10)));
		inputContainer.add(passwordText);
		labelsContainer.add(labelProfile);
		labelsContainer.add(Box.createRigidArea(new Dimension(0,10)));
		labelsContainer.add(labelPassword);
		mainContainer.add(elementsContainer);
		mainContainer.add(Box.createRigidArea(new Dimension(0,15)));
		mainContainer.add(buttonsContainer);
		elementsContainer.add(labelsContainer);
		elementsContainer.add(Box.createRigidArea(new Dimension(20,0)));
		elementsContainer.add(inputContainer);
	}
	/**
	 * Deja el panel en espera
	 */
	public void loading(){
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		profileCombo.setEnabled(false);
		passwordText.setEnabled(false);
		buttonAccept.setEnabled(false);
		buttonCancel.setEnabled(false);
	}
	
	/**
	 * Resume el estado del panel
	 */
	public void resume(){
		profileCombo.setEnabled(true);
		passwordText.setEnabled(true);
		buttonAccept.setEnabled(true);
		buttonCancel.setEnabled(true);
		setCursor(Cursor.getDefaultCursor());
	}
	
	public JButton getAcceptButton(){
		return buttonAccept;
	}
	
	public JButton getCancelButton(){
		return buttonCancel;
	}
	
	public JComboBox<String> getProfileCheck(){
		return profileCombo;
	}
	
	public JPasswordField getPassword(){
		return passwordText;
	}
}
