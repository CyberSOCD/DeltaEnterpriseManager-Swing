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
public class ProfileFrame extends JFrame{
	private LoginPanel loginPanel;
	private JComboBox<String> profileCombo;
	private JPasswordField passwordText;
	private JButton cancelButton;
	private JButton loginButton;
	private final String passAM = "saturno";
	private final String passMIN = "pacifico";
	private final String profMIN = "Minorista";
	private final String profAM = "Mayorista";
	private final String profAdm = "Administrador";
	private String profile = profMIN;
	private boolean login = false;
	private boolean AM = false;
	private boolean MIN = false;
	
	public ProfileFrame(){
		setTitle("Delta Enterprise Manager");
		setSize(400, 150);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setLayout(new GridLayout(1,1));
		initialize();
		loginPanel.loading();
		//Se fuerza la carga inicial de un WebClass para evitar 
		//que muestre tiempos incorrectos la validacion
		try {
			WebClass c = new WebClass();
			c.setURL("http://localhost", "");
		} catch (Exception e) {
			
		}
		loginPanel.resume();
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
		profileCombo = loginPanel.getProfileCheck();
		profileCombo.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				profile = profileCombo.getSelectedItem().toString();
			}
		});
		profileCombo.setSelectedItem(profMIN);
		
		passwordText = loginPanel.getPassword();
		passwordText.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					validateProfile();
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
					validateProfile();
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
				validateProfile();
			}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		
		profileCombo.setVisible(true);
		passwordText.setVisible(true);
		loginButton.setVisible(true);
		cancelButton.setVisible(true);
		add(loginPanel);
		validate();
		repaint();
		passwordText.requestFocus();
	}
	
	private void validateProfile(){
		boolean loginOk = true;
		if(profile.equals(profAM)){
			if(!parsePassword(passwordText.getPassword()).isEmpty()
					&& parsePassword(passwordText.getPassword()).equals(passAM)){
				AM = true;
			}else
				loginOk = false;
		}else if(profile.equals(profMIN)){
			if(!parsePassword(passwordText.getPassword()).isEmpty()
					&& parsePassword(passwordText.getPassword()).equals(passMIN)){
				MIN = true;
			}else
				loginOk = false;
		}else if(profile.equals(profAdm)){
			if(!parsePassword(passwordText.getPassword()).isEmpty() 
					&& parsePassword(passwordText.getPassword()).equals("AdminGcDEM")){
				AM = true;
				MIN = true;
			}else
				loginOk = false;
		}
		if(!loginOk){
			login = false;
			JOptionPane.showMessageDialog(null, "La contraseña no es correcta");
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
				listEnv.add(userData);
				num++;
			}
			InitialFrame frame = new InitialFrame(listEnv, 
					this.isMayoristas(), this.isMinoristas());
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
