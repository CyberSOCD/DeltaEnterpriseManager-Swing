package uiFrames;

import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import uiFrames.InitialFrame;
import uiPanels.LoginPanel;
import uiProfiles.ProfileControl;
import uiProfiles.ProfileConstants;
import common.UserConnectionData;
import drivers.WebClass;
import fileAccess.LoadFile;
import fuentes.EnvironmentData;

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
	private JPasswordField passwordText;
	private JButton cancelButton;
	private JButton loginButton;
	private final String passAM = "saturno";
	private final String passMIN = "pacifico";
	private final String sysMIN = "Minorista";
	private final String sysAM = "Mayorista";
	private final String sysAdm = "Administrador";
	private String system = sysMIN;
	private boolean login = false;
	private boolean AM = false;
	private boolean MIN = false;
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
			}
		});
		systemCombo.setSelectedItem(sysMIN);
		
		profileCombo = loginPanel.getProfileCheck();
//		profileCombo.addItemListener(new ItemListener(){
//			@Override
//			public void itemStateChanged(ItemEvent e) {
//				profile = profileCombo.getSelectedItem().toString();
//			}
//		});
		profileCombo.setSelectedItem(ProfileConstants.gestion);
		
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
			if(!parsePassword(passwordText.getPassword()).isEmpty()
					&& parsePassword(passwordText.getPassword()).equals(passAM)){
				AM = true;
			}else
				loginOk = false;
		}else if(system.equals(sysMIN)){
			if(!parsePassword(passwordText.getPassword()).isEmpty()
					&& parsePassword(passwordText.getPassword()).equals(passMIN)){
				MIN = true;
			}else
				loginOk = false;
		}else if(system.equals(sysAdm)){
			if(!parsePassword(passwordText.getPassword()).isEmpty() 
					&& parsePassword(passwordText.getPassword()).equals("AdminGcDEM")){
				AM = true;
				MIN = true;
			}else
				loginOk = false;
		}
		if(!loginOk){
			login = false;
			JOptionPane.showMessageDialog(null, "La contraseņa no es correcta");
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
	
	private String parsePassword(char[] array){
		String pass = "";
		for(char c:array){
			pass = pass + c;
		}
		return pass;
	}
}
