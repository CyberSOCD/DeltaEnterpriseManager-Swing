package obfuscatorPanelTest;

import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import conexion.fileAccess.LoadFile;
import controlador.common.UserConnectionData;
import controlador.fuentes.EnvironmentData;
import vista.ui.Profiles.ProfileConstants;
import vista.ui.Profiles.ProfileControl;

@SuppressWarnings("serial")
public class SimpleMainFrame extends JFrame {

	private ProfileControl profile;
	private ArrayList<UserConnectionData> data;
	
	private final static String testProfile = ProfileConstants.GESTION_MIN;
	private final static String testUser = "SGCv10";
	private final static String testPass = "SGCv10";
	private final static String testEnv = "Test Evolutivo L1";
	private final static String testEnvKey = "TEST_ID50_L1";
	private final static String testDDBB = "proydelbd1.intranet.gasnaturalfenosa.com:1521/PROYDELBD1_DDELTA10";
	private final static String testDns = "http://dvdelap10web1.intranet.gasnaturalfenosa.com";
	private final static int testNum = 1;
	private JPanel child;
	/**
	 * Permite testear el comportamiento de un panel
	 * 
	 * @param testingPanel
	 */
	public SimpleMainFrame() {
		setTitle("Delta Enterprise Manager");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(true);
//		setLayout(new GridLayout(1,1));
//		setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
		setExtendedState(MAXIMIZED_BOTH);
		initializeSimple();
	}
	
	public SimpleMainFrame(boolean completeData){
		setTitle("Delta Enterprise Manager");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(true);
//		setLayout(new GridLayout(1,1));
		setExtendedState(MAXIMIZED_BOTH);
		if(completeData){
			initialize();
		}else{
			initializeSimple();
		}
	}
	
	public void setSubPanel(JPanel panel){
		add(panel);
		validate();
		repaint();
	}
	
	public void setSubPanel(JPanel panel, LayoutManager lyt){
		getContentPane().setLayout(lyt);
		getContentPane().add(panel);
		getContentPane().validate();
		getContentPane().repaint();
	}
	
	public ProfileControl getProfile(){
		return profile;
	}
	
	public ArrayList<UserConnectionData> getData(){
		return data;
	}

	/**
	 * Carga de valores minimos para realizar una prueba basica de un componente
	 */
	private void initializeSimple() {
		UserConnectionData u = new UserConnectionData(testUser, testPass, testEnv, testDns, testNum);
		u.setDbHost(testDDBB);
		u.setEnvKey(testEnvKey);
		data = new ArrayList<UserConnectionData>();
		data.add(u);
		profile = new ProfileControl(testProfile, data);
	}
	/**
	 * Carga valores reales para simular tamaños y margenes
	 */
	private void initialize(){
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
		profile = new ProfileControl(testProfile, listEnv);
		this.data = new ArrayList<UserConnectionData>();
		this.data.addAll(listEnv);
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

}
