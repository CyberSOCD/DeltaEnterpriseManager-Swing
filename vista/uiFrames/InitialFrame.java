package uiFrames;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import common.UserConnectionData;
import uiPanels.EnvironmentPanel;
import uiPanels.TestingPanel;
import uiProfiles.ProfileControl;

/**
 * 
 * Frame principal de la aplicación da acceso a todas las funcionalidades
 * 
 */
@SuppressWarnings("serial")
public class InitialFrame extends JFrame {
	private EnvironmentPanel validationPanel;
	private TestingPanel testPanel;
	private boolean isMinoristas;
	private boolean isMayoristas;
	private ProfileControl prof;

	/**
	 * Constructor inicializa el objeto que maneja los datos
	 */
	public InitialFrame(ArrayList<UserConnectionData> data, boolean isAm, boolean isMin, ProfileControl profile) {
		// Inicializa el objeto Webclass para evitar falsear tiempos de
		// respuesta
		prof = profile;
		isMayoristas = isAm;
		isMinoristas = isMin;
		validationPanel = new EnvironmentPanel(data,isMayoristas, isMinoristas, profile);
		testPanel = new TestingPanel(data, isMayoristas, isMinoristas, profile);
		setLayout(new GridLayout(1, 2));
		initialize();
	}

	/**
	 * Inicializa los elementos de la UI
	 */
	private void initialize() {
		setTitle("Delta Enterprise Manager - " + prof.getSelectedProfile());
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		this.setVisible(true);
		// this.add(validationPanel);
		// this.add(testPanel);
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				validationPanel, testPanel);
		split.setDividerLocation(300);
		this.add(split);
	}
}
