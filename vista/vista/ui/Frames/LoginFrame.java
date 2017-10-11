package vista.ui.Frames;

import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import vista.ui.Frames.InitialFrame;
import vista.ui.Panels.LoginPanel;
import vista.ui.Profiles.ProfileConstants;
import vista.ui.Profiles.ProfileControl;
import conexion.drivers.WebClass;
import conexion.fileAccess.LoadFile;
import controlador.common.UserConnectionData;
import controlador.fuentes.EnvironmentData;
import controlador.security.SecurityClass;

/**
 * 
 * 
 * 
 */
@SuppressWarnings("serial")
public class LoginFrame extends JFrame{
	private LoginPanel loginPanel;
	private JComboBox<String> systemCombo;
	private JComboBox<String> profileCombo;
	private DefaultComboBoxModel<String> profileModel;
	private JPasswordField passwordText;
	private JButton cancelButton;
	private JButton loginButton;
	private final String sysMIN = "Minorista";
	private final String sysAM = "Mayorista";
	private String system = sysMIN;
	private boolean login = false;
	private boolean AM = false;
	private boolean MIN = true;
	private ProfileControl profileControl;
	
	public LoginFrame(){
		setTitle("Delta Enterprise Manager");
		setSize(400, 180);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setLayout(new GridLayout(1,1));
		initialize();
		loginPanel.loading();
		//Se fuerza la carga inicial de un WebClass para evitar 
		//que muestre tiempos incorrectos en la validacion
		try {
			WebClass c = new WebClass();
			c.setURL("http://localhost", "");
		} catch (Exception e) {
			
		}
		loginPanel.resume();
		passwordText.requestFocus();
	}
	
	private boolean isMinoristas(){
		return MIN;
	}
	private boolean isMayoristas(){
		return AM;
	}
	/**
	 * Inicializa los elementos de la UI
	 */
	private void initialize() {
		loginPanel = new LoginPanel();
		systemCombo = loginPanel.getSystemCheck();
		systemCombo.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				system = systemCombo.getSelectedItem().toString();
				updateProfileItems(system);
				if(system.equals(sysMIN)){
					MIN = true;
					AM = false;
				}else if(system.equals(sysAM)){
					AM = true;
					MIN = false;
				}
			}
		});
		systemCombo.setSelectedItem(sysMIN);
		
		profileCombo = loginPanel.getProfileCheck();
		profileCombo.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(profileCombo.getSelectedItem() == null)
					return;
				if (profileCombo.getSelectedItem().toString()
						.equals(ProfileConstants.ADMINISTRADOR)) {
					systemCombo.setEnabled(false);
				}else{
					systemCombo.setEnabled(true);
				}
			}
		});
		profileCombo.setSelectedItem(ProfileConstants.GESTION_MIN);
		
		passwordText = loginPanel.getPassword();
		this.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
					login = false;
					dispose();
				}else if(e.getKeyCode()==KeyEvent.VK_ENTER){
					validateSystem();
				}
			}
		});
		passwordText.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					validateSystem();
				}else if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
					login = false;
					dispose();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {
//				if(e.getKeyCode()==KeyEvent.VK_F1){
//					loadFrame("Desa");
//				}
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					validateSystem();
				}else if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
					login = false;
					dispose();
				}
			}
		});
		cancelButton = loginPanel.getCancelButton();
		cancelButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {
				login = false;
				dispose();
			}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		
		loginButton = loginPanel.getAcceptButton();
		loginButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {
				validateSystem();
			}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		profileCombo.setVisible(true);
		systemCombo.setVisible(true);
		passwordText.setVisible(true);
		loginButton.setVisible(true);
		cancelButton.setVisible(true);
		add(loginPanel);
		validate();
		repaint();
	}
	
	private void validateSystem(){
		boolean loginOk = true;
		if(system.equals(sysAM)){
			if(!passwordText.getPassword().toString().isEmpty()
					&& passwordText.getPassword().toString().equals("saturno") && loginOk){
				AM = true;
			}else
				loginOk = false;
		}else if(system.equals(sysMIN)){
			if(!passwordText.getPassword().toString().isEmpty()
					&& passwordText.getPassword().toString().equals("pacifico")){
				MIN = true;
			}else
				loginOk = false;
		}else if(system.equals(sysMIN)){
			if(!passwordText.getPassword().toString().isEmpty()
					&& passwordText.getPassword().toString().equals("PassAdminis")){
				MIN = true;
				AM = true;
			}else
				loginOk = false;
		}
		SecurityClass sec = new SecurityClass(systemCombo.getSelectedItem()
				.toString(), profileCombo.getSelectedItem().toString(),
				passwordText.getPassword());
		if(!sec.validProfile(passwordText.getPassword())){
			login = false;
			JOptionPane.showMessageDialog(null, "La contraseña no es correcta");
			passwordText.setText("");
		}
		else{
			login = true;
			loadFrame();
		}
	}
	
	private boolean isLogin(){
		return login;
	}
	
	private void loadFrame(){
		this.setVisible(false);
		if(this.isLogin()){
			EnvironmentData data = new EnvironmentData();
			ArrayList<UserConnectionData> listEnv = new ArrayList<UserConnectionData>();
			int num = 0;
			for (String nombre : data.getNamesList()) {
				String user = getLoginInf(nombre, "USER");
				String pass = getLoginInf(nombre, "PASSWORD");
				String dns = data.getURL(nombre);
				String envName = data.getFormatName(nombre);
				String dataBase = data.getDatabase(nombre);
				UserConnectionData userData = new UserConnectionData(user, pass,
						envName, dns, num);
				userData.setDbHost(dataBase);
				userData.setEnvKey(data.getEnvKey(nombre));
				listEnv.add(userData);
				num++;
			}
			profileControl = new ProfileControl((String) profileCombo.getSelectedItem(), listEnv);
			InitialFrame frame = new InitialFrame(listEnv, 
					this.isMayoristas(), this.isMinoristas(),profileControl);
			frame.getTitle();
		}
	}
	
//	private void loadFrame(String Desa){
//		this.setVisible(false);
//		EnvironmentData data = new EnvironmentData();
//		ArrayList<UserConnectionData> listEnv = new ArrayList<UserConnectionData>();
//		int num = 0;
//		for (String nombre : data.getNamesList()) {
//			String user = getLoginInf(nombre, "USER");
//			String pass = getLoginInf(nombre, "PASSWORD");
//			String dns = data.getURL(nombre);
//			String envName = data.getFormatName(nombre);
//			String dataBase = data.getDatabase(nombre);
//			UserConnectionData userData = new UserConnectionData(user, pass,
//					envName, dns, num);
//			userData.setDbHost(dataBase);
//			userData.setEnvKey(data.getEnvKey(nombre));
//			listEnv.add(userData);
//			num++;
//		}
//		profileControl = new ProfileControl(Desa, listEnv);
//		InitialFrame frame = new InitialFrame(listEnv, 
//				this.isMayoristas(), this.isMinoristas(),profileControl);
//		frame.getTitle();
//	}
	
	private String getLoginInf(String environmentName, String type) {
		LoadFile.class.getClass().getResource("/resources/EnvironmentList.properties");
		ArrayList<String> testCases = LoadFile.getFile("/users.properties");
		String returnValue = "";
		String regex = "";
		if (environmentName.contains(" AM"))
			regex = "_MAY";
		else
			regex = "_MIN";
		for (String line : testCases) {
			if (line.contains(type + regex)) {
				returnValue = line.split("=")[1];
			}
		}
		return returnValue;
	}
	
	private void updateProfileItems(String system){
		//Limpia items
		profileModel = (DefaultComboBoxModel<String>) profileCombo.getModel();
		profileModel.removeAllElements();
		//Rellena items
		if(systemCombo.getSelectedItem().toString().equals(sysMIN)){
			profileModel.addElement(ProfileConstants.CERTIFICACION_MIN);
			profileModel.addElement(ProfileConstants.GESTION_MIN);
			profileModel.addElement(ProfileConstants.ADMINISTRADOR);
//			profileModel.addElement(ProfileConstants.GESTION_MIN);
		}else{
			profileModel.addElement(ProfileConstants.GESTION_MAY);
			profileModel.addElement(ProfileConstants.ADMINISTRADOR);
//			profileCombo.setSelectedItem(ProfileConstants.GESTION_MAY);
		}
		profileCombo.setModel(profileModel);
//		profileModel = (DefaultComboBoxModel<String>) profileCombo.getModel();
	}
}
