package vista.ui.Tabs;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import vista.ui.Dialog.MessageCenterDialog;
import vista.ui.Panels.ObfuscatorPanel;
import vista.ui.Profiles.ProfileControl;
import vista.ui.Profiles.ProfileControl.TipoSistema;
import controlador.common.UserConnectionData;

@SuppressWarnings("serial")
public class ObfuscatorValidationTab extends GenericControlTab {
	
	private ProfileControl profile;
	private ArrayList<UserConnectionData> fullListData;
	//JPanels
	private JPanel mainPanel;
	private ObfuscatorPanel validationsPanel;
	private JPanel buttonsPanel;
	private JPanel panelActive;
	private TipoSistema sistema;

	public ObfuscatorValidationTab(ArrayList<UserConnectionData> ListData,
			TipoSistema tipo, ProfileControl profile) {
		super(tipo,profile);
		sistema = tipo;
		this.profile = profile;
		this.fullListData = ListData;
		initialize();
		loadComponent();
	}

	@Override
	public void loadComponent() {
		
	}
	
	/**
	 * Inicializa los componentes del panel
	 */
	private void initialize(){
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout(5, 5));
		setLayout(new GridLayout(1,1));
		add(mainPanel);
		validationsPanel = new ObfuscatorPanel(fullListData, profile, sistema);
		panelActive = panelMinorista;
		buttonsPanel = new JPanel();
		
		GridBagConstraints gridBag = new GridBagConstraints();
		gridBag.fill = GridBagConstraints.HORIZONTAL;
		gridBag.ipady = 70;
		gridBag.ipadx = 70;
		gridBag.weightx = 0.0;
		gridBag.gridwidth = 3;
		gridBag.gridx = 0;
		gridBag.gridy = 0;
		mainPanel.add(profilePanel, BorderLayout.NORTH);
		gridBag.fill = GridBagConstraints.BOTH;
		gridBag.ipady = 70;
		gridBag.weightx = 0.0;
		gridBag.gridwidth = 3;
		gridBag.gridx = 0;
		gridBag.gridy = 1;
		mainPanel.add(validationsPanel, BorderLayout.WEST);
		gridBag.fill = GridBagConstraints.HORIZONTAL;
		gridBag.ipady = 70;
		gridBag.ipadx = 70;
		gridBag.weightx = 0.0;
		gridBag.gridwidth = 3;
		gridBag.gridx = 0;
		gridBag.gridy = 2;
		mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
	}
	
	@Override
	protected void changeSelected(JPanel panel){
		if(validationsPanel.isValidating()){
			MessageCenterDialog.showWarningDialog(this, "Se está validando un entorno, no se puede cambiar de sistema");
			return;
		}
		if(!panel.equals(panelActive)){
			panelActive = panel;
			if(panelActive.equals(panelMayorista)){
				unSelected(panelMinorista);
				sistema = TipoSistema.MAYORISTA;
			}
			else{
				unSelected(panelMayorista);
				sistema = TipoSistema.MINORISTA;
			}
			setSelected(panelActive);
			validationsPanel.changeSystem(sistema);
		}
	}
}
