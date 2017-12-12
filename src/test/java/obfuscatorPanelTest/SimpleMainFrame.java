package obfuscatorPanelTest;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controlador.common.UserConnectionData;
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
		setLayout(new GridLayout(1,1));
		setExtendedState(MAXIMIZED_BOTH);
		initialize();
	}
	
	public void setSubPanel(JPanel panel){
		add(panel);
		validate();
		repaint();
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
	private void initialize() {
		UserConnectionData u = new UserConnectionData(testUser, testPass, testEnv, testDns, testNum);
		u.setDbHost(testDDBB);
		u.setEnvKey(testEnvKey);
		data = new ArrayList<UserConnectionData>();
		data.add(u);
		profile = new ProfileControl(testProfile, data);
	}

}
