package vista.ui.Tabs;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import vista.ui.Profiles.ProfileControl;
import controlador.common.UserConnectionData;

@SuppressWarnings("serial")
public class ObfuscatorValidationTab extends GenericControlTab {
	
	private ProfileControl profile;
	private ArrayList<UserConnectionData> listData;
	//JPanels
	private JPanel mainPanel;
	private JPanel validationsPanel;
	private JPanel buttonsPanel;
	private JPanel panelActive;

	public ObfuscatorValidationTab(ArrayList<UserConnectionData> ListData,
			boolean isAM, boolean isMIN, ProfileControl profile) {
		super(isAM,isMIN,profile);
		this.profile = profile;
		this.listData = ListData;
		initialize();
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
		validationsPanel = new JPanel();
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
		gridBag.fill = GridBagConstraints.HORIZONTAL;
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
		if(!panel.equals(panelActive)){
			panelActive = panel;
			if(panelActive.equals(panelMayorista)){
				unSelected(panelMinorista);
//				pan.reloadComponents(getList(), true, false);
				isMayoristas = true;
				isMinoristas = false;
			}
			else{
				unSelected(panelMayorista);
//				pan.reloadComponents(getList(), false, true);
				isMayoristas = false;
				isMinoristas = true;
			}
			setSelected(panelActive);
		}
	}
}
