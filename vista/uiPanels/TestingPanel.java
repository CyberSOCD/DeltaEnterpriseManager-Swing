package uiPanels;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import common.UserConnectionData;
import uiProfiles.ProfileControl;
import uiTabs.GenericControlTab;
import uiTabs.HealthValidationTab;

@SuppressWarnings("serial")
public class TestingPanel extends JPanel {

	private ProfileControl profile;
	private JTabbedPane genericPanel;
	private GenericControlTab validacionEntornos;
//	private GenericControlTab validacionSatelitales;
	private static final int validationIndexTab = 0;

	/**
	 * 
	 * @param ListData
	 * @param isAM
	 * @param isMIN
	 */
	public TestingPanel(ArrayList<UserConnectionData> ListData, boolean isAM,boolean isMIN, ProfileControl profile) {
		this.profile = profile;
		setLayout(new GridLayout(1, 1));
		validacionEntornos = new HealthValidationTab(ListData,isAM,isMIN,this.profile);
//		validacionSatelitales = new HealthValidationTab(ListData, isAM, isMIN, profile);
		initialize();
	}
	
	public void selectValidation(){
		genericPanel.setVisible(true);
		genericPanel.setSelectedIndex(validationIndexTab);
	}

	/**
	 * Iniciliza todos los componentes del Componente
	 */
	private void initialize() {

		genericPanel = new JTabbedPane();
		genericPanel.addTab("Validación entornos", validacionEntornos);
//		genericPanel.addTab("Validación satelitales", validacionSatelitales);
		add(genericPanel);
		
		genericPanel.setVisible(false);
		
	}
}
