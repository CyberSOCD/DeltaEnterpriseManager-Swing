package vista.ui.Panels;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import controlador.common.UserConnectionData;
import vista.ui.Profiles.ProfileControl;
import vista.ui.Profiles.ProfileControl.TipoSistema;
import vista.ui.Tabs.GenericControlTab;
import vista.ui.Tabs.HealthValidationTab;
import vista.ui.Tabs.ObfuscatorValidationTab;
import vista.ui.Tabs.ReportingControlTab;

@SuppressWarnings("serial")
public class TestingPanel extends JPanel {

	private ProfileControl profile;
	private JTabbedPane genericPanel;
	private GenericControlTab validacionEntornos;
	private GenericControlTab validacionOfuscado;
	private GenericControlTab generacionInformes;
	private static final int validationIndexTab = 0;
	private static final int ofuscadoIndexTab = 1;
	private static final int informeIndexTab = 2;
	private TipoSistema sistema;

	/**
	 * 
	 * @param ListData
	 * @param isAM
	 * @param isMIN
	 */
	public TestingPanel(ArrayList<UserConnectionData> ListData, TipoSistema tipo, ProfileControl profile) {
		this.profile = profile;
		setLayout(new GridLayout(1, 1));
		sistema = tipo;
		validacionEntornos = new HealthValidationTab(ListData,sistema,this.profile);
		validacionOfuscado = new ObfuscatorValidationTab(ListData, sistema, profile);
		generacionInformes = new ReportingControlTab(ListData, sistema, profile);
		initialize();
	}
	
	public void selectValidation(){
		genericPanel.setVisible(true);
		genericPanel.setSelectedIndex(validationIndexTab);
	}
	
	public void selectObfuscado(){
		genericPanel.setVisible(true);
		genericPanel.setSelectedIndex(ofuscadoIndexTab);
	}
	
	public void selectReporting(){
		genericPanel.setVisible(true);
		genericPanel.setSelectedIndex(informeIndexTab);
	}

	/**
	 * Iniciliza todos los componentes del Componente
	 */
	private void initialize() {

		genericPanel = new JTabbedPane();
		genericPanel.addTab("Validación entornos", validacionEntornos);
		if(profile.isAdmin()){
			genericPanel.addTab("Validación Ofuscado", validacionOfuscado);
			genericPanel.addTab("Generacion Informes", generacionInformes);
		}
		add(genericPanel);
		
		genericPanel.setVisible(false);
		
	}
}
