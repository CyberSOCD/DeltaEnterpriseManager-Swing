package uiPanels;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import common.UserConnectionData;
import uiProfiles.ProfileControl;
import uiTabs.GenericControlTab;
import uiTabs.HealthValidationTab;

@SuppressWarnings("serial")
public class TestingPanel extends JPanel {

	private ProfileControl profile;
	private JTabbedPane genericPanel;
	private GenericControlTab validacionEntornos;

	/**
	 * 
	 * @param ListData
	 * @param isAM
	 * @param isMIN
	 */
	public TestingPanel(ArrayList<UserConnectionData> ListData, boolean isAM,boolean isMIN, ProfileControl profile) {
		this.profile = profile;
		setLayout(new GridLayout(1, 1));
		validacionEntornos = new HealthValidationTab(ListData,isAM,isMIN,profile);
		initialize();
	}

	/**
	 * Iniciliza todos los componentes del Componente
	 */
	private void initialize() {

		genericPanel = new JTabbedPane();
		genericPanel.addTab("Validación entornos", validacionEntornos);
		add(genericPanel);
		
		genericPanel.setSelectedIndex(-1);
		
	}
	
	private void loadTab(GenericControlTab tab){
		tab.loadComponent();
	}
}
